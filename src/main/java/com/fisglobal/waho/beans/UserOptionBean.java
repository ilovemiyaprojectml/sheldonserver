package com.fisglobal.waho.beans;

public class UserOptionBean {
	private int userId;
	private String optionCode;
	private String optionValue;
	
	public UserOptionBean(int userId, String optionCode, String optionValue) {
		super();
		this.userId = userId;
		this.optionCode = optionCode;
		this.optionValue = optionValue;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getOptionCode() {
		return optionCode;
	}
	public void setOptionCode(String optionCode) {
		this.optionCode = optionCode;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	
	
}
