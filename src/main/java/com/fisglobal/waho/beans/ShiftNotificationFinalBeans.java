package com.fisglobal.waho.beans;

public class ShiftNotificationFinalBeans {
	
	private int schedTimeId;
	private String date;
	private String time;
	private String status;
	private String reason;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getSchedTimeId() {
		return schedTimeId;
	}
	public void setSchedTimeId(int schedTimeId) {
		this.schedTimeId = schedTimeId;
	}
	public ShiftNotificationFinalBeans(int schedTimeId, String date, String time, String status, String reason) {
		super();
		this.schedTimeId = schedTimeId;
		this.date = date;
		this.time = time;
		this.status = status;
		this.reason = reason;
	}
	public ShiftNotificationFinalBeans() {
		
	}

	
	
	
	
}
