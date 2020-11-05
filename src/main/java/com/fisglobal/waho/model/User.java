package com.fisglobal.waho.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="wh_users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	private String userEid;
	private String firstName;
	private String lastName;
	private String email;
	private int userParentId;
	private LocalDateTime effectiveStartDate;
	private LocalDateTime effectiveEndDate;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;
	private int createdBy;
	private int lastUpdatedBy;
	private String isActive;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ShiftSchedule> shiftSchedules;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserRole> userRoles;

	public User() {
		this.dateCreated = LocalDateTime.now();
		this.dateLastUpdated = LocalDateTime.now();
		this.createdBy = 0;
		this.lastUpdatedBy = 0;
		this.isActive = "Y";
	}

	public User(String userEid, String firstName, String lastName, String email, int userParentId, LocalDateTime effectiveStartDate,
			LocalDateTime effectiveEndDate, LocalDateTime dateCreated, LocalDateTime dateLastUpdated, int createdBy, int lastUpdatedBy,
			String isActive) {
		super();
		this.userEid = userEid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userParentId = userParentId;
		this.effectiveStartDate = effectiveStartDate;
		this.effectiveEndDate = effectiveEndDate;
		this.dateCreated = dateCreated;
		this.dateLastUpdated = dateLastUpdated;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.isActive = isActive;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getUserParentId() {
		return userParentId;
	}

	public void setUserParentId(int userParentId) {
		this.userParentId = userParentId;
	}

	public LocalDateTime getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(LocalDateTime effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public LocalDateTime getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(LocalDateTime effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
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

	public List<ShiftSchedule>  getShiftSchedules() {
		return shiftSchedules;
	}

	public void setShiftSchedules(List<ShiftSchedule> shiftSchedules) {
		this.shiftSchedules = shiftSchedules;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userEid=" + userEid + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", userParentId=" + userParentId + ", effectiveStartDate=" + effectiveStartDate
				+ ", effectiveEndDate=" + effectiveEndDate + ", dateCreated=" + dateCreated + ", dateLastUpdated="
				+ dateLastUpdated + ", createdBy=" + createdBy + ", lastUpdatedBy=" + lastUpdatedBy + ", isActive="
				+ isActive + "]";
	}

}
