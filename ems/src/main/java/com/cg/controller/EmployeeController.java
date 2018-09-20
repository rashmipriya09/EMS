package com.cg.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cg.entities.EmployeeBean;
import com.cg.entities.LeaveBean;
import com.cg.entities.UserMasterBean;
import com.cg.exception.DateException;
import com.cg.exception.EmployeeException;
import com.cg.service.IEmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	IEmployeeService service;

	static String name;
	EmployeeBean employee = null;
	LeaveBean leave = null;
	String post = null;
	HttpSession session = null;

	@RequestMapping("index")
	public ModelAndView toIndex() {

		ModelAndView mnv = new ModelAndView();
		mnv.setViewName("index");
		return mnv;
	}

	@RequestMapping("login")
	public String adminPage(Model model) {
		UserMasterBean user = new UserMasterBean();
		model.addAttribute("user", user);
	
		return "login";
	}

	@RequestMapping("loginDetails")
	public String validLoginDetails(
			@Valid @ModelAttribute("user") UserMasterBean user,
			BindingResult result, Model model, HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "login";
		} else {
			try {
				employee = service.getDetailsByName(user.getUserName());
				UserMasterBean bean = service.isValid(user.getUserName(),
						user.getPassword());
				String type = bean.getUserType();
				
				if (type.equals("admin")) {
					name = user.getUserName();
					model.addAttribute("name", name);
					model.addAttribute("employee", employee);
					String designation = employee.getDesignation();

					if ((designation.equals("Manager"))
							|| (designation.equals("manager"))) {
						post = designation;
						model.addAttribute("flag", post);
					}

					return "adminPage";
				} else if (type.equals("user")) {
					name = user.getUserName();
					model.addAttribute("name", name);
					model.addAttribute("employee", employee);
					String designation = employee.getDesignation();

					if ((designation.equals("Manager"))
							|| (designation.equals("manager"))) {

						post = designation;
						model.addAttribute("flag", post);
					}

					return "userPage";
				} else {
					model.addAttribute("msg", "YOU ARE NOT AN ADMIN OR A USER");
					return "login";
				}
			} catch (EmployeeException e) {
				model.addAttribute("msg", e.getMessage());
				return "error";
			}
		}

	}

	@RequestMapping("admin")
	public String adminRedirect(Model model) {

		model.addAttribute("name", name);
		String uname = name;
		employee = service.getDetailsByName(uname);
		String designation = employee.getDesignation();
		if ((designation.equals("Manager")) || (designation.equals("manager"))) {
			post = designation;
			model.addAttribute("flag", post);
		}

		
		return "adminPage";
	}

	@RequestMapping("add")
	public String toAddEmpoyee(Model model) {
		
		model.addAttribute("temp", 0);
		EmployeeBean bean = new EmployeeBean();
		model.addAttribute("employee", bean);
		model.addAttribute("grade", new String[] { "M1", "M2", "M3", "M4",
				"M5", "M6", "M7" });
		model.addAttribute("maritalStatus", new String[] { "S", "M", "D", "W" });
		return "addEmployee";
	}

	@RequestMapping("addEmployee")
	public String addEmp(
			@Valid @ModelAttribute("employee") EmployeeBean employee,
			BindingResult result, Model model) {
		
		Date dob = employee.getDateOfBirth();
		Date doj = employee.getDateOfJoining();
		if (result.hasErrors() || dob == null || doj == null) {
			if (dob == null)
				model.addAttribute("dob", "DOB cannot be null");
			if (doj == null)
				model.addAttribute("doj", "DOJ cannot be null");
			model.addAttribute("grade", new String[] { "M1", "M2", "M3", "M4",
					"M5", "M6", "M7" });
			model.addAttribute("maritalStatus", new String[] { "S", "M", "D",
					"W" });
			model.addAttribute("employee", employee);
			model.addAttribute("temp", 0);

			return "addEmployee";
		} else {
			try {

				employee.setRemainingLeaves(12);

				service.isValid(employee);

				service.addEmployee(employee);
				service.generateLogin(employee);
				model.addAttribute("temp", 1);
				model.addAttribute("id", employee.getEmployeeId());

				return "addEmployee";
			} catch (EmployeeException e) {
				model.addAttribute("msg", e.getMessage());
				return "error";
			} catch (DateException e) {

				model.addAttribute("temp", 0);
				model.addAttribute("message", e.getMessage());
				return "addEmployee";
			}
		}

	}

	@RequestMapping("modify")
	public String modifyPage(Model model) {
		model.addAttribute("temp", 0);
		EmployeeBean bean = new EmployeeBean();
		model.addAttribute("bean", bean);
		return "modifyPage";
	}

	@RequestMapping("modifyDetails")
	public String modifyDetails(
			@Valid @ModelAttribute("bean") EmployeeBean bean,
			BindingResult result, Model model) {
		if (result.getFieldError("employeeId") != null) {
			model.addAttribute("temp", 0);
			return "modifyPage";
		}
		try {

			EmployeeBean employee = service
					.getDetailsById(bean.getEmployeeId());
			if(employee==null)
			{
				model.addAttribute("msg","EmployeeID does not exist");
				model.addAttribute("temp", 0);
				return "modifyPage";
			}
			model.addAttribute("employee", employee);
			model.addAttribute("temp", 1);
			return "modifyPage";
		} catch (EmployeeException e) {
			model.addAttribute("msg", e.getMessage());
			return "error";
		}

	}

	@RequestMapping("update")
	public String updateDetails(
			@Valid @ModelAttribute("employee") EmployeeBean bean,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("employee", bean);
			model.addAttribute("temp", 1);
			return "modifyPage";
		} else {
			try {
				if (service.modifyEmp(bean)) {
					model.addAttribute("bean", bean);
					return "updatedEmpDetails";
				} else {
					model.addAttribute("msg",
							"COULD NOT UPDATE EMPLOYEE DETAILS");
					return "error";
				}
			} catch (EmployeeException e) {
				model.addAttribute("msg", e.getMessage());
				return "error";
			}
		}
	}

	@RequestMapping("display")
	public String displayEmployeeDetails(Model model) {
		try {
			List<EmployeeBean> list = service.displayAll();
			if (list.isEmpty()) {
				model.addAttribute("msg", "EMPTY LIST");
				return "error";
			} else {
				model.addAttribute("list", list);
				return "displayEmployee";
			}
		} catch (EmployeeException e) {
			model.addAttribute("msg", e.getMessage());
			return "error";
		}

	}

	@RequestMapping("home")
	public String toReturnHome(Model model) {
		return "index";
	}

	@RequestMapping("userPage")
	public String toReturnUserPage(Model model) {
		model.addAttribute("name", name);
		String uname = name;
		employee = service.getDetailsByName(uname);
		String designation = employee.getDesignation();
		if ((designation.equals("Manager")) || (designation.equals("manager"))) {
			post = designation;
			model.addAttribute("flag", post);
		}
		return "userPage";
	}

	@RequestMapping("search")
	public String toSearch(Model model) {
		return "searchEmployee";
	}

	@RequestMapping("searchEmployee")
	public String toSearchEmp(@RequestParam("search") String name, Model model) {
		EmployeeBean bean = new EmployeeBean();
		model.addAttribute("employee", bean);
		model.addAttribute("name", name);
		model.addAttribute("id", "employeeId");
		model.addAttribute("fname", "firstName");
		model.addAttribute("lname", "lastName");
		model.addAttribute("grade", "grade");
		model.addAttribute("maritalStatus", "maritalStatus");
		model.addAttribute("isFirst", "true");
		return "searchEmployee";

	}

	@RequestMapping("searchEmp")
	public String toSearchEmployee(
			@ModelAttribute("employee") EmployeeBean bean, Model model) {
		try {
			List<EmployeeBean> list = service.search(bean);
			if (list.isEmpty()) {
				String msg = "The field should not be Empty or doesn't exist exist or it is invalid "+"Search Again";
				model.addAttribute("msg", msg);
				return "searchEmployee";
			}
			model.addAttribute("plist", list);
			return "searchEmp";
		} catch (EmployeeException e) {
			model.addAttribute("msg", "Unable to display list");
			return "error";
		}
	}

	@RequestMapping("applyLeave")
	public String applyLeave(Model model) {
		LeaveBean bean = new LeaveBean();
		model.addAttribute("leave", bean);
		return "applyLeave";

	}

	@RequestMapping("addLeaveDetails")
	public String addLeaveDetail(
			@Valid @ModelAttribute("leave") LeaveBean bean,
			@ModelAttribute("employee") EmployeeBean employee,
			BindingResult result, Model model) {

		model.addAttribute("name", name);
		String uname = name;
		employee = service.getDetailsByName(uname);
		String designation = employee.getDesignation();
		if ((designation.equals("Manager")) || (designation.equals("manager"))) {
			post = designation;
			model.addAttribute("flag", post);
		}

		try {
			service.addLeaveDetails(this.employee, bean);
		} catch (EmployeeException e) {
			model.addAttribute("message", e.getMessage());
			return "applyLeave";
		}
		return "userPage";

	}

	@RequestMapping("checkLeaves.obj")
	public String checkLeaves(@ModelAttribute("employee") LeaveBean employee,
			Model model) {
		ArrayList<LeaveBean> list = service.fetchLeaveDetails(this.employee
				.getEmployeeId());
		LeaveBean leave = new LeaveBean();
		model.addAttribute("list", list);
		model.addAttribute("employee", leave);
		return "leaveDetails";
	}

	@RequestMapping("checkLeavesUser.obj")
	public String checkLeavesUser(
			@ModelAttribute("employee") LeaveBean employee, Model model) {
		ArrayList<LeaveBean> list = service.fetchLeaveDetails(this.employee
				.getEmployeeId());
		LeaveBean leave = new LeaveBean();
		model.addAttribute("list", list);
		model.addAttribute("employee", leave);
		if (list.isEmpty()) {
			model.addAttribute("flag", 1);
		}
		return "leaveDetailsUser";
	}

	@RequestMapping(value = "/leaveStatus.obj", params = "approve")
	public String leaveApprove(@ModelAttribute("employee") LeaveBean employee,
			Model model) {
		service.approveLeave(employee.getEmployeeId());
		ArrayList<LeaveBean> list = service.fetchLeaveDetails(this.employee
				.getEmployeeId());
		model.addAttribute("list", list);
		return "leaveDetails";
	}

	@RequestMapping(value = "/leaveStatus.obj", params = "reject")
	public String leaveReject(@ModelAttribute("employee") LeaveBean employee,
			Model model) {

		service.rejectLeave(employee.getEmployeeId());
		ArrayList<LeaveBean> list = service.fetchLeaveDetails(this.employee
				.getEmployeeId());
		model.addAttribute("list", list);
		return "leaveDetails";
	}
}
