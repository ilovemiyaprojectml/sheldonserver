package com.fisglobal.waho.beans;

import java.util.List;

public class UserBean {
	private int userId;
	private String username;
	private String firstName;
	private String lastName;
	private int userParentId;
	private String password;
	private String otp;
	private String role;
	private String token;
	private String msg;
	private List<UserOptionBean> userOptions;
	
	public UserBean() {}
	

	public UserBean(int userId, String username, String firstName, String lastName, int userParentId, String password,
			String otp, String role, String token, String msg, List<UserOptionBean> userOptions) {
		super();
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userParentId = userParentId;
		this.password = password;
		this.otp = otp;
		this.role = role;
		this.token = token;
		this.msg = msg;
		this.userOptions = userOptions;
	}
	
	


	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOtp() {
		return otp;
	}


	public void setOtp(String otp) {
		this.otp = otp;
	}


	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public List<UserOptionBean> getUserOptions() {
		return userOptions;
	}


	public void setUserOptions(List<UserOptionBean> userOptions) {
		this.userOptions = userOptions;
	}




}
