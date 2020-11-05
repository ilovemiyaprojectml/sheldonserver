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
@Table(name="wh_shift_sched_times")
public class ShiftScheduleTime {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int schedTimeId;
	private LocalDateTime schedTime;
	private String schedTimeStatus;
	private String remarks;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;
	private int createdBy;
	private int lastUpdatedBy;
	private String isActive;
	private String isAcknowledged;
	private String temp;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="shift_sched_id")
	private ShiftSchedule shiftSchedule;
	
	public ShiftScheduleTime() {}
	
	public ShiftScheduleTime(ShiftSchedule shiftSchedule, LocalDateTime schedTime,
			String schedTimeStatus, String remarks, LocalDateTime dateCreated, LocalDateTime dateLastUpdated,
			int createdBy, int lastUpdatedBy, String isActive) {
		super();
		this.shiftSchedule = shiftSchedule;
		this.schedTime = schedTime;
		this.schedTimeStatus = schedTimeStatus;
		this.remarks = remarks;
		this.dateCreated = dateCreated;
		this.dateLastUpdated = dateLastUpdated;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.isActive = isActive;
	}



	public int getSchedTimeId() {
		return schedTimeId;
	}

	public void setSchedTimeId(int schedTimeId) {
		this.schedTimeId = schedTimeId;
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

	public ShiftSchedule getShiftSchedule() {
		return shiftSchedule;
	}

	public void setShiftSchedule(ShiftSchedule shiftSchedule) {
		this.shiftSchedule = shiftSchedule;
	}

	@Override
	public String toString() {
		return String.format(
				"ShiftScheduleTime [schedTimeId=%s, schedTime=%s, orderPriority=%s, schedTimeStatus=%s, remarks=%s, dateCreated=%s, dateLastUpdated=%s, createdBy=%s, lastUpdatedBy=%s, isActive=%s, shiftSchedule=%s]",
				schedTimeId, schedTime, schedTimeStatus, remarks, dateCreated, dateLastUpdated,
				createdBy, lastUpdatedBy, isActive, shiftSchedule);
	}

	public String getIsAcknowledged() {
		return isAcknowledged;
	}

	public void setIsAcknowledged(String isAcknowledged) {
		this.isAcknowledged = isAcknowledged;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

}
