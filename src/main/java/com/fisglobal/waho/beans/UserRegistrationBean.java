package com.fisglobal.waho.beans;

import com.fisglobal.waho.model.ShiftSchedule;

public class UserRegistrationBean {
	private int userId;
	private String userEid;
	private String firstName;
	private String lastName;
	private String email;
	private int adminId;
	private int userParentId;
	private String effectiveStartDate;
	private String effectiveEndDate;
	private int numMissedLogins;
	private String schedStartDate;
	private String endStartDate;
	private ShiftSchedule shiftSched;
	
	private String[] roles;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserEid() {
		return userEid;
	}
	public void setUserEid(String userEid) {
		this.userEid = userEid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getUserParentId() {
		return userParentId;
	}
	public void setUserParentId(int userParentId) {
		this.userParentId = userParentId;
	}
	public String getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(String effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public String getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(String effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	public int getNumMissedLogins() {
		return numMissedLogins;
	}
	public void setNumMissedLogins(int numMissedLogins) {
		this.numMissedLogins = numMissedLogins;
	}
	public String getSchedStartDate() {
		return schedStartDate;
	}
	public void setSchedStartDate(String schedStartDate) {
		this.schedStartDate = schedStartDate;
	}
	public String getEndStartDate() {
		return endStartDate;
	}
	public void setEndStartDate(String endStartDate) {
		this.endStartDate = endStartDate;
	}
	public ShiftSchedule getShiftSched() {
		return shiftSched;
	}
	public void setShiftSched(ShiftSchedule shiftSched) {
		this.shiftSched = shiftSched;
	}
	public String[] getRoles() {
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

}
