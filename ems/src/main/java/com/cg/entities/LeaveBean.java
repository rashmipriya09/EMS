package com.cg.entities;

import java.sql.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="leave_history")
public class LeaveBean {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="leave_id")
	private Integer leaveId;
	
	@Column(name="emp_id")
	private String employeeId;
	
	@Column(name="leave_balance")
	private Integer remainingLeaves;
	
	@Column(name="status")
	private String leaveStatus;
	
	@Column(name="date_from")
	private Date startdate;
	
	@Column(name="noofdays_applied")
	private Integer duration;
	
	@Column(name="mgr_id")
	private String managerId;
	
	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	@Column(name="date_to")
	private Date enddate;
	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Integer getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Integer leaveId) {
		this.leaveId = leaveId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getRemainingLeaves() {
		return remainingLeaves;
	}

	public void setRemainingLeaves(Integer remainingLeaves) {
		this.remainingLeaves = remainingLeaves;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Leave [leaveId=" + leaveId + ", employeeId=" + employeeId
				+ ", remainingLeaves=" + remainingLeaves + ", leaveStatus="
				+ leaveStatus + ", startdate=" + startdate + ", duration="
				+ duration + "]";
	}

	
}
