package com.cg.dao;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Repository;

import com.cg.entities.EmployeeBean;
import com.cg.entities.LeaveBean;
import com.cg.entities.UserMasterBean;
import com.cg.exception.EmployeeException;

@Repository
public class EmployeeDaoImpl implements IEmployeeDao {

	Logger logger = Logger.getRootLogger();

	public EmployeeDaoImpl() {
		PropertyConfigurator.configure("src/main/resources//log4j.properties");
	}

	@PersistenceContext
	EntityManager entity;

	@Override
	public void addEmployee(EmployeeBean employeebean) throws EmployeeException {
		entity.persist(employeebean);
		entity.flush();
		logger.info("Employee Added Successfully");
	}

	@Override
	public List<EmployeeBean> displayAll() throws EmployeeException {
		String sql = "SELECT bean FROM EmployeeBean bean";
		TypedQuery<EmployeeBean> query = entity.createQuery(sql,
				EmployeeBean.class);
		List<EmployeeBean> list = query.getResultList();
		return list;
	}

	@Override
	public EmployeeBean getDetailsById(String employeeId)
			throws EmployeeException {
		logger.info("Employee found by ID");
		return entity.find(EmployeeBean.class, employeeId);

	}

	@Override
	public List<String> getGrades() throws EmployeeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modifyEmp(EmployeeBean bean) throws EmployeeException {
		String sql = "UPDATE EmployeeBean SET firstName=:firstName,lastName=:lastName,dateOfBirth=:dateOfBirth,dateOfJoining=:dateOfJoining,departmentId=:departmentId,grade=:grade,designation=:designation,salary=:salary,gender=:gender,maritalStatus=:maritalStatus,address=:address,phoneNumber=:phoneNumber,managerId=:managerId WHERE employeeId=:employeeId";
		Query query = entity.createQuery(sql);
		query.setParameter("employeeId", bean.getEmployeeId());
		query.setParameter("firstName", bean.getFirstName());
		query.setParameter("lastName", bean.getLastName());
		query.setParameter("dateOfBirth", bean.getDateOfBirth());
		query.setParameter("dateOfJoining", bean.getDateOfJoining());
		query.setParameter("departmentId", bean.getDepartmentId());
		query.setParameter("grade", bean.getGrade());
		query.setParameter("designation", bean.getDesignation());
		query.setParameter("salary", bean.getSalary());
		query.setParameter("gender", bean.getGender());
		query.setParameter("maritalStatus", bean.getMaritalStatus());
		query.setParameter("address", bean.getAddress());
		query.setParameter("phoneNumber", bean.getPhoneNumber());
		query.setParameter("managerId", bean.getManagerId());
		int status = query.executeUpdate();
		if (status > 0) {
			logger.info("Employee modified successfully");
			return true;
		} else {
			logger.error("Employee could not be modified");
			return false;

		}

	}

	@Override
	public List<EmployeeBean> search(EmployeeBean bean)
			throws EmployeeException {

		String sql = "SELECT bean FROM EmployeeBean bean";

		TypedQuery<EmployeeBean> query = entity.createQuery(sql,
				EmployeeBean.class);
		List<EmployeeBean> list = query.getResultList();
		List<EmployeeBean> l = new ArrayList<EmployeeBean>();

		for (EmployeeBean e : list) {
			if (e.getEmployeeId().equals(bean.getEmployeeId())
					|| e.getFirstName().equals(bean.getFirstName())
					|| e.getLastName().equals(bean.getLastName())
					|| e.getGrade().equals(bean.getGrade())
					|| e.getMaritalStatus().equals(bean.getMaritalStatus()))
				l.add(e);
		}
		return l;
	}

	@Override
	public String getDeptName(int deptId) throws EmployeeException {

		return null;
	}

	@Override
	public boolean validDetails(EmployeeBean bean) throws EmployeeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserMasterBean isValid(String user, String pass)
			throws EmployeeException {

		try {
			String sql = "SELECT bean FROM UserMasterBean bean WHERE bean.userName=:user AND bean.password=:pass";
			TypedQuery<UserMasterBean> query = entity.createQuery(sql,
					UserMasterBean.class);
			query.setParameter("user", user);
			query.setParameter("pass", pass);
			UserMasterBean bean = query.getSingleResult();
			logger.info("User Details Vaildated");
			return bean;
		} catch (Exception e) {
			logger.error("User Details Could Not Be Vaildated");
			throw new EmployeeException(
					"Does not match with database,Please try Logging again!!!");
		}
	}

	@Override
	public LeaveBean addLeaveDetails(EmployeeBean employee, LeaveBean bean) {

		bean.setEmployeeId(employee.getEmployeeId());

		bean.setRemainingLeaves(employee.getRemainingLeaves());
		bean.setManagerId(employee.getManagerId());
		bean.setLeaveStatus("applied");
		Integer workdays = bean.getDuration();

		if (workdays < 1) {

			bean.setEnddate(bean.getStartdate());
		}
		LocalDate result = bean.getStartdate().toLocalDate();
		int addedDays = 0;
		while (addedDays < workdays) {

			result = result.plusDays(1);
			if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result
					.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				++addedDays;
			}
		}
		bean.setEnddate(java.sql.Date.valueOf(result));

		entity.persist(bean);
		logger.info("Leave Details Added Successfully");
		return bean;
	}

	@Override
	public EmployeeBean getDetailsByName(String userName) {
		String sql = "select e from EmployeeBean e where e.firstName=:userName";
		TypedQuery<EmployeeBean> query = entity.createQuery(sql,
				EmployeeBean.class);
		query.setParameter("userName", userName);
		EmployeeBean bean = query.getSingleResult();
		return bean;
	}

	@Override
	public ArrayList<LeaveBean> fetchLeaveDetails(String employeeid) {
		String sql = "Select e from LeaveBean e where e.managerId=:eId";
		TypedQuery<LeaveBean> query = entity.createQuery(sql, LeaveBean.class);
		query.setParameter("eId", employeeid);
		ArrayList<LeaveBean> list = (ArrayList<LeaveBean>) query
				.getResultList();
		logger.info("Leave Details Fetched Successfully");
		return list;
	}

	@Override
	public void approveLeave(String employeeId) {
		String sql = "select bean from LeaveBean bean where employeeId=:employeeId";

		TypedQuery<LeaveBean> query = entity.createQuery(sql, LeaveBean.class);
		query.setParameter("employeeId", employeeId);
		LeaveBean bean = query.getSingleResult();
		bean.setLeaveStatus("approved");
		int balance = bean.getRemainingLeaves() - bean.getDuration();
		bean.setRemainingLeaves(balance);
		String sql1 = "update EmployeeBean bean set bean.remainingLeaves="
				+ balance + "where employeeId=:employeeId";

		Query query1 = entity.createQuery(sql1);
		query1.setParameter("employeeId", employeeId);
		int i = query1.executeUpdate();
		if (i != 0) {
			entity.remove(bean);
			logger.info("Leave Approved Successfully");
		} else
			logger.info("Leave Could not be approved");
	}

	@Override
	public void rejectLeave(String employeeId) {
		String sql = "select bean from LeaveBean bean where employeeId=:employeeId";

		TypedQuery<LeaveBean> query = entity.createQuery(sql, LeaveBean.class);
		query.setParameter("employeeId", employeeId);
		LeaveBean bean = query.getSingleResult();
		bean.setLeaveStatus("rejected");
		entity.remove(bean);
		logger.info("Leave Rejected Successfully");
	}

	@Override
	public void generateLogin(EmployeeBean employee) {
		UserMasterBean user = new UserMasterBean();
		user.setUserId(employee.getEmployeeId());
		user.setUserName(employee.getFirstName());
		user.setPassword(employee.getFirstName() + "123");
		user.setUserType("user");
		entity.persist(user);
		logger.info("Login Details Generated Successfully");
	}

}
