package com.fisglobal.waho.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="wh_user_roles")
public class UserRole {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userRoleId;
	private String roleCd;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;
	private int createdBy;
	private int lastUpdatedBy;
	private String isActive;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private User user;
	
	public UserRole() {
		this.dateCreated = LocalDateTime.now();
		this.dateLastUpdated = LocalDateTime.now();
		this.createdBy = 0;
		this.lastUpdatedBy = 0;
		this.isActive = "Y";
	}

	public UserRole(int userRoleId, String roleCd, LocalDateTime dateCreated, LocalDateTime dateLastUpdated,
			int createdBy, int lastUpdatedBy, String isActive) {
		super();
		this.userRoleId = userRoleId;
		this.roleCd = roleCd;
		this.dateCreated = dateCreated;
		this.dateLastUpdated = dateLastUpdated;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.isActive = isActive;
	}

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getRoleCd() {
		return roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return String.format(
				"UserRole [userRoleId=%s, roleCd=%s, dateCreated=%s, dateLastUpdated=%s, createdBy=%s, lastUpdatedBy=%s, isActive=%s]",
				userRoleId, roleCd, dateCreated, dateLastUpdated, createdBy, lastUpdatedBy, isActive);
	}

}
