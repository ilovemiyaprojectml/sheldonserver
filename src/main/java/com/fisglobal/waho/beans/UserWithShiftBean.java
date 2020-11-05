package com.fisglobal.waho.beans;

public class UserWithShiftBean {

	
	private int userId;
	private String userEId;
	private String firstName;
	private String lastName;
	

	
	
	public UserWithShiftBean() {
		
	}
	
	public UserWithShiftBean(int userId, String userEId, String firstName, String lastName) {
		super();
		this.userId = userId;
		this.userEId = userEId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserEId() {
		return userEId;
	}
	public void setUserEId(String userEId) {
		this.userEId = userEId;
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
	
	
	
	
	
	
	
}
