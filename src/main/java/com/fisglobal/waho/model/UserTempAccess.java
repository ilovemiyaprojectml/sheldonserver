package com.fisglobal.waho.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wh_idp_users")
public class UserTempAccess {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int tempAccId;
	private int userId;
	private String userEid;
	private String password;
	private String resetpassword;
	private String otpCode;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;
	private int createdBy;
	private int lastUpdatedBy;
	private String isActive;

	public UserTempAccess() {}

	public UserTempAccess(int tempAccId, int userId, String userEid, String password, String resetpassword, String otpCode,
			LocalDateTime dateCreated, LocalDateTime dateLastUpdated, int createdBy, int lastUpdatedBy,
			String isActive) {
		super();
		this.tempAccId = tempAccId;
		this.userId = userId;
		this.userEid = userEid;
		this.password = password;
		this.resetpassword = resetpassword;
		this.otpCode = otpCode;
		this.dateCreated = dateCreated;
		this.dateLastUpdated = dateLastUpdated;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.isActive = isActive;
	}

	public int getTempAccId() {
		return tempAccId;
	}

	public void setTempAccId(int tempAccId) {
		this.tempAccId = tempAccId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserEid() {
		return userEid;
	}

	public void setUserEid(String userEid) {
		this.userEid = userEid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetpassword() {
		return resetpassword;
	}

	public void setResetpassword(String resetpassword) {
		this.resetpassword = resetpassword;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
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
				"UserTempAccess [tempAccId=%s, userId=%s, userEid=%s, password=%s, resetpassword=%s, otpCode=%s, dateCreated=%s, dateLastUpdated=%s, createdBy=%s, lastUpdatedBy=%s, isActive=%s]",
				tempAccId, userId, userEid, password, otpCode, dateCreated, dateLastUpdated, createdBy, lastUpdatedBy,
				isActive);
	}
}
