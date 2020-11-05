package com.fisglobal.waho.beans;

import java.time.LocalDateTime;

public class UserABean {
	private int user_id;
	private String eid;
	private String firstname;
	private String lastname;
	private String email;
	private String[] role;
	private String manager;
	private LocalDateTime effective_start_date;
	private LocalDateTime effective_end_date;
	private LocalDateTime date_created;
	private LocalDateTime date_last_updated;
	private String created_by;
	private String last_updated_by;
	private String is_active;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String[] getRole() {
		return role;
	}
	public void setRole(String[] role) {
		this.role = role;
	}
	public LocalDateTime getEffective_start_date() {
		return effective_start_date;
	}
	public void setEffective_start_date(LocalDateTime effective_start_date) {
		this.effective_start_date = effective_start_date;
	}
	public LocalDateTime getEffective_end_date() {
		return effective_end_date;
	}
	public void setEffective_end_date(LocalDateTime effective_end_date) {
		this.effective_end_date = effective_end_date;
	}
	public LocalDateTime getDate_created() {
		return date_created;
	}
	public void setDate_created(LocalDateTime date_created) {
		this.date_created = date_created;
	}
	public LocalDateTime getDate_last_updated() {
		return date_last_updated;
	}
	public void setDate_last_updated(LocalDateTime date_last_updated) {
		this.date_last_updated = date_last_updated;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getLast_updated_by() {
		return last_updated_by;
	}
	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	

}
