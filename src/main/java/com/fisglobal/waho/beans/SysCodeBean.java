package com.fisglobal.waho.beans;

import java.time.LocalDateTime;

public class SysCodeBean {
	private int code_id;
	private String code_name;
	private String desc;
	private String category;
	private String value_1;
	private String value_2;
	private String value_3;
	private String value_4;
	
	private LocalDateTime date_created;
	private LocalDateTime date_last_updated;
	private String created_by;
	private String last_updated_by;
	private String is_active;
	public int getCode_id() {
		return code_id;
	}
	public void setCode_id(int code_id) {
		this.code_id = code_id;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getValue_1() {
		return value_1;
	}
	public void setValue_1(String value_1) {
		this.value_1 = value_1;
	}
	public String getValue_2() {
		return value_2;
	}
	public void setValue_2(String value_2) {
		this.value_2 = value_2;
	}
	public String getValue_3() {
		return value_3;
	}
	public void setValue_3(String value_3) {
		this.value_3 = value_3;
	}
	public String getValue_4() {
		return value_4;
	}
	public void setValue_4(String value_4) {
		this.value_4 = value_4;
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
	

}
