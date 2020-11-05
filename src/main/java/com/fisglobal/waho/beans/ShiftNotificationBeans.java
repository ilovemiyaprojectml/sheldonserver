package com.fisglobal.waho.beans;

import java.time.LocalDateTime;

public class ShiftNotificationBeans {
	
	
	private LocalDateTime schedTime;
	private String schedTimeStatus;
	private String remarks;
	
	
	public ShiftNotificationBeans() {
		
	}


	public ShiftNotificationBeans(LocalDateTime schedTime, String schedTimeStatus, String remarks) {
		super();
		this.schedTime = schedTime;
		this.schedTimeStatus = schedTimeStatus;
		this.remarks = remarks;
	}


	public LocalDateTime getSchedTime() {
		return schedTime;
	}


	public void setSchedTime(LocalDateTime schedTime) {
		this.schedTime = schedTime;
	}


	public String getSchedTimeStatus() {
		return schedTimeStatus;
	}


	public void setSchedTimeStatus(String schedTimeStatus) {
		this.schedTimeStatus = schedTimeStatus;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
