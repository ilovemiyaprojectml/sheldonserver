package com.fisglobal.waho.beans;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ReportBean {
	private List<String> emp;
	private List<Integer> emp_userid;
	private List<String> emp_disp;
	private List<String> mgr;
	private List<Integer> mgr_userid;
	private List<String> mgr_disp;
	private List<String> status;
//	private LocalDateTime scheddatefrom;
//	private LocalDateTime scheddateto;
	
	private Date scheddatefrom;
	private Date scheddateto;
	
	private String type;
	private String format;
	
	private String last_updated_by;

	public List<String> getEmp() {
		return emp;
	}

	public void setEmp(List<String> emp) {
		this.emp = emp;
	}

	public String getLast_updated_by() {
		return last_updated_by;
	}

	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}

	public List<Integer> getEmp_userid() {
		return emp_userid;
	}

	public void setEmp_userid(List<Integer> emp_userid) {
		this.emp_userid = emp_userid;
	}

	public List<String> getEmp_disp() {
		return emp_disp;
	}

	public void setEmp_disp(List<String> emp_disp) {
		this.emp_disp = emp_disp;
	}

	public List<String> getMgr() {
		return mgr;
	}

	public void setMgr(List<String> mgr) {
		this.mgr = mgr;
	}

	public List<Integer> getMgr_userid() {
		return mgr_userid;
	}

	public void setMgr_userid(List<Integer> mgr_userid) {
		this.mgr_userid = mgr_userid;
	}

	public List<String> getMgr_disp() {
		return mgr_disp;
	}

	public void setMgr_disp(List<String> mgr_disp) {
		this.mgr_disp = mgr_disp;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public Date getScheddatefrom() {
		return scheddatefrom;
	}

	public void setScheddatefrom(Date scheddatefrom) {
		this.scheddatefrom = scheddatefrom;
	}

	public Date getScheddateto() {
		return scheddateto;
	}

	public void setScheddateto(Date scheddateto) {
		this.scheddateto = scheddateto;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

//	public LocalDateTime getScheddatefrom() {
//		return scheddatefrom;
//	}
//
//	public void setScheddatefrom(LocalDateTime scheddatefrom) {
//		this.scheddatefrom = scheddatefrom;
//	}
//
//	public LocalDateTime getScheddateto() {
//		return scheddateto;
//	}
//
//	public void setScheddateto(LocalDateTime scheddateto) {
//		this.scheddateto = scheddateto;
//	}
	

}
