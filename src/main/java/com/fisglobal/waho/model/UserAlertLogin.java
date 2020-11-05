package com.fisglobal.waho.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wh_user_alert_logins")
public class UserAlertLogin {
	
	@Id
	private int alertLoginId;
	private int shiftSchedId;
	private LocalDateTime dateLoggedIn;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;
	private int createdBy;
	private int lastUpdatedBy;
	private String isActive;
	
	public UserAlertLogin() {}

	public UserAlertLogin(int alertLoginId, int shiftSchedId, LocalDateTime dateLoggedIn, LocalDateTime dateCreated,
			LocalDateTime dateLastUpdated, int createdBy, int lastUpdatedBy, String isActive) {
		super();
		this.alertLoginId = alertLoginId;
		this.shiftSchedId = shiftSchedId;
		this.dateLoggedIn = dateLoggedIn;
		this.dateCreated = dateCreated;
		this.dateLastUpdated = dateLastUpdated;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.isActive = isActive;
	}

	public int getAlertLoginId() {
		return alertLoginId;
	}

	public void setAlertLoginId(int alertLoginId) {
		this.alertLoginId = alertLoginId;
	}

	public int getShiftSchedId() {
		return shiftSchedId;
	}

	public void setShiftSchedId(int shiftSchedId) {
		this.shiftSchedId = shiftSchedId;
	}

	public LocalDateTime getDateLoggedIn() {
		return dateLoggedIn;
	}

	public void setDateLoggedIn(LocalDateTime dateLoggedIn) {
		this.dateLoggedIn = dateLoggedIn;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDateTime getDateLastUpdated() {
		return dateLastUpdated;
	}

	public void setDateLastUpdated(LocalDateTime dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return String.format(
				"UserAlertLogin [alertLoginId=%s, shiftSchedId=%s, dateLoggedIn=%s, dateCreated=%s, dateLastUpdated=%s, createdBy=%s, lastUpdatedBy=%s, isActive=%s]",
				alertLoginId, shiftSchedId, dateLoggedIn, dateCreated, dateLastUpdated, createdBy, lastUpdatedBy,
				isActive);
	}

}
