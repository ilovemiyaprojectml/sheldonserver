package com.fisglobal.waho.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wh_user_options")
public class UserOption {
	
	@Id
	private int userOptId;
	private int userId;
	private String optionCode;
	private String optionValue;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;
	private int createdBy;
	private int lastUpdatedBy;
	private String isActive;
	
	public UserOption() {}

	public UserOption(int userId, String optionCode, String optionValue) {
		super();
		this.userId = userId;
		this.optionCode = optionCode;
		this.optionValue = optionValue;
		this.isActive = "Y";
	}
	
	public UserOption(int userOptId, int userId, String optionCode, String optionValue, LocalDateTime dateCreated,
			LocalDateTime dateLastUpdated, int createdBy, int lastUpdatedBy, String isActive) {
		super();
		this.userOptId = userOptId;
		this.userId = userId;
		this.optionCode = optionCode;
		this.optionValue = optionValue;
		this.dateCreated = dateCreated;
		this.dateLastUpdated = dateLastUpdated;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.isActive = isActive;
	}




	public int getUserOptId() {
		return userOptId;
	}

	public void setUserOptId(int userOptId) {
		this.userOptId = userOptId;
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
