package com.fisglobal.waho.beans;



import com.fisglobal.waho.model.ShiftSchedule;
import com.fisglobal.waho.model.User;

public class EmployeeBean {
	private User user;
	private String schedStartDate;
	private String schedEndDate;
	private String numMissedLogin;
	private String actualEndDate;
	
	
	public EmployeeBean(User user, ShiftSchedule shiftSchedule, int numMissedLogin) {
		super();
		this.user = user;
		if (shiftSchedule!=null) {
			
			this.numMissedLogin = numMissedLogin + "";
			try {
				this.schedStartDate = shiftSchedule.getSchedStartDate().toString();
			}catch(NullPointerException e) {
				this.schedStartDate = "-";
			}
			
			try {
				this.schedEndDate = shiftSchedule.getSchedEndDate().toString();
			}catch(NullPointerException e) {
				this.schedEndDate = "-";
			}
			
			try {
				this.actualEndDate = shiftSchedule.getActualEndDate().toString();
			}catch(NullPointerException e) {
				this.actualEndDate = "-";
			}
		} 
		
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String getNumMissedLogin() {
		return numMissedLogin;
	}

	public void setNumMissedLogin(String numMissedLogin) {
		this.numMissedLogin = numMissedLogin;
	}

	public String getSchedStartDate() {
		return schedStartDate;
	}

	public void setSchedStartDate(String schedStartDate) {
		this.schedStartDate = schedStartDate;
	}

	public String getSchedEndDate() {
		return schedEndDate;
	}

	public void setSchedEndDate(String schedEndDate) {
		this.schedEndDate = schedEndDate;
	}

	public String getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}
	

	
	
}
