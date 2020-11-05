package com.fisglobal.waho.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wh_user_sessions")
public class UserSession {
	
	@Id
	private int sessionId;
	private String sessionUuid;
	private int userId;
	private LocalDateTime dateLoggedIn;
	private LocalDateTime dateLoggedOut;
	private LocalDateTime dateTimeout;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;
	private int createdBy;
	private int lastUpdatedBy;
	private String isActive;
	
	public UserSession() {}

	public UserSession(int sessionId, String sessionUuid, int userId, LocalDateTime dateLoggedIn,
			LocalDateTime dateLoggedOut, LocalDateTime dateTimeout, LocalDateTime dateCreated,
			LocalDateTime dateLastUpdated, int createdBy, int lastUpdatedBy, String isActive) {
		super();
		this.sessionId = sessionId;
		this.sessionUuid = sessionUuid;
		this.userId = userId;
		this.dateLoggedIn = dateLoggedIn;
		this.dateLoggedOut = dateLoggedOut;
		this.dateTimeout = dateTimeout;
		this.dateCreated = dateCreated;
		this.dateLastUpdated = dateLastUpdated;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.isActive = isActive;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionUuid() {
		return sessionUuid;
	}

	public void setSessionUuid(String sessionUuid) {
		this.sessionUuid = sessionUuid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public LocalDateTime getDateLoggedIn() {
		return dateLoggedIn;
	}

	public void setDateLoggedIn(LocalDateTime dateLoggedIn) {
		this.dateLoggedIn = dateLoggedIn;
	}

	public LocalDateTime getDateLoggedOut() {
		return dateLoggedOut;
	}

	public void setDateLoggedOut(LocalDateTime dateLoggedOut) {
		this.dateLoggedOut = dateLoggedOut;
	}

	public LocalDateTime getDateTimeout() {
		return dateTimeout;
	}

	public void setDateTimeout(LocalDateTime dateTimeout) {
		this.dateTimeout = dateTimeout;
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
				"UserSession [sessionId=%s, sessionUuid=%s, userId=%s, dateLoggedIn=%s, dateLoggedOut=%s, dateTimeout=%s, dateCreated=%s, dateLastUpdated=%s, createdBy=%s, lastUpdatedBy=%s, isActive=%s]",
				sessionId, sessionUuid, userId, dateLoggedIn, dateLoggedOut, dateTimeout, dateCreated, dateLastUpdated,
				createdBy, lastUpdatedBy, isActive);
	}

}
