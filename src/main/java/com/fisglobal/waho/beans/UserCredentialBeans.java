package com.fisglobal.waho.beans;

public class UserCredentialBeans {
	
	
	public String username;
	public String password;
	public String email;
	public String newpassword;
	public String confirmnewpassword;
	
	
	
	public UserCredentialBeans() {
		
	}



	public UserCredentialBeans(String usernameString, String password, String email, String newpassword, String confirmnewpassword) {
		super();
		this.username = usernameString;
		this.password = password;
		this.email = email;
		this.newpassword = newpassword;
		this.confirmnewpassword = confirmnewpassword;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String usernameString) {
		this.username = usernameString;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getConfirmnewpassword() {
		return confirmnewpassword;
	}



	public void setConfirmnewpassword(String confirmnewpassword) {
		this.confirmnewpassword = confirmnewpassword;
	}



	public String getNewpassword() {
		return newpassword;
	}



	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	
	
	
}
