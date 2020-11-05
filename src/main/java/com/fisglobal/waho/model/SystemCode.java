package com.fisglobal.waho.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wh_sys_codes")
public class SystemCode {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int sysCodeId;
	private String sysCodeCategory;
	private String sysCodeName;
	private String sysCode;
	private String value1;
	private String value2;
	private String value3;
	private String value4;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;
	private int createdBy;
	private int lastUpdatedBy;
	private String isActive;
	
	public SystemCode() {}
	
	public SystemCode(int sysCodeId, String sysCodeCategory, String sysCodeName, String sysCode, String value1,
			String value2, String value3, String value4, LocalDateTime dateCreated, LocalDateTime dateLastUpdated,
			int createdBy, int lastUpdatedBy, String isActive) {
		super();
		this.sysCodeId = sysCodeId;
		this.sysCodeCategory = sysCodeCategory;
		this.sysCodeName = sysCodeName;
		this.sysCode = sysCode;
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.value4 = value4;
		this.dateCreated = dateCreated;
		this.dateLastUpdated = dateLastUpdated;
		this.createdBy = createdBy;
		this.lastUpdatedBy = lastUpdatedBy;
		this.isActive = isActive;
	}

	public int getSysCodeId() {
		return sysCodeId;
	}

	public void setSysCodeId(int sysCodeId) {
		this.sysCodeId = sysCodeId;
	}

	public String getSysCodeCategory() {
		return sysCodeCategory;
	}

	public void setSysCodeCategory(String sysCodeCategory) {
		this.sysCodeCategory = sysCodeCategory;
	}

	public String getSysCodeName() {
		return sysCodeName;
	}

	public void setSysCodeName(String sysCodeName) {
		this.sysCodeName = sysCodeName;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
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
				"SystemCode [sysCodeId=%s, sysCodeCategory=%s, sysCodeName=%s, sysCode=%s, value1=%s, value2=%s, value3=%s, value4=%s, dateCreated=%s, dateLastUpdated=%s, createdBy=%s, lastUpdatedBy=%s, isActive=%s]",
				sysCodeId, sysCodeCategory, sysCodeName, sysCode, value1, value2, value3, value4, dateCreated,
				dateLastUpdated, createdBy, lastUpdatedBy, isActive);
	}

}
