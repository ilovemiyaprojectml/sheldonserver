package com.fisglobal.waho.beans;

public class AcknowledgeBean {

	private int schedTimeId;
	private String remarks;

	public AcknowledgeBean(int schedTimeId) {
		super();
		this.schedTimeId = schedTimeId;
		this.remarks = "";
	}
	
	public AcknowledgeBean(int schedTimeId, String remarks) {
		super();
		this.schedTimeId = schedTimeId;
		this.remarks = remarks;
	}

	public AcknowledgeBean() {
		
	}

	public int getSchedTimeId() {
		return schedTimeId;
	}

	public void setSchedTimeId(int schedTimeId) {
		this.schedTimeId = schedTimeId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
