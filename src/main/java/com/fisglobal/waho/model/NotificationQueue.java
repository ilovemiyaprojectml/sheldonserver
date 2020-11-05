package com.fisglobal.waho.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.netty.handler.ssl.PemPrivateKey;

@Entity
@Table(name = "wh_notification_queue")
public class NotificationQueue {
	
	
	@Id
	private int notifId;
	private int userId;
	private LocalTime notifSched;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;
	private int createdBy;
	private int lastUpdatedBy;
	private String isActive;
	
	public NotificationQueue(int notifId, int userId, LocalTime notifSched) {
		super();
		this.notifId = notifId;
		this.userId = userId;
		this.notifSched = notifSched;
		this.dateCreated = LocalDateTime.now();
		this.dateLastUpdated = LocalDateTime.now();
		this.createdBy = -1; // system
		this.lastUpdatedBy = -1; //system
		this.isActive = "Y";
	}

	public NotificationQueue(int notifId, int userId, LocalTime notifSched, LocalDateTime dateCreated,
			LocalDateTime dateLastUpdated, int createdBy, int lastUpdatedBy, String isActive) {
		super();
		this.notifId = notifId;
		this.userId = userId;
		this.notifSched = notifSched;
		this.dateCreated = dateCreated;
		this.dateLastUpdated = dateLastUpdated;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.isActive = isActive;
	}

	public NotificationQueue() {
		
	}

	public int getNotifId() {
		return notifId;
	}

	public void setNotifId(int notifId) {
		this.notifId = notifId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public LocalTime getNotifSched() {
		return notifSched;
	}

	public void setNotifSched(LocalTime notifSched) {
		this.notifSched = notifSched;
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
	
	
	
	
	
	
}
