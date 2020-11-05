package com.fisglobal.waho.beans;

public class ShiftScheduleBean {
    private String shiftStartDate;
    private String shiftStartTime;
    private String shiftEndDate;
    private String shiftEndTime;
    private String allowedBreaks;
    
    public ShiftScheduleBean() {}

	public ShiftScheduleBean(String shiftStartDate, String shiftStartTime, String shiftEndDate, String shiftEndTime, String allowedBreaks) {
		super();
		this.shiftStartDate = shiftStartDate;
		this.shiftStartTime = shiftStartTime;
		this.shiftEndDate = shiftEndDate;
		this.shiftEndTime = shiftEndTime;
		this.allowedBreaks = allowedBreaks;
	}

	public String getShiftStartDate() {
		return shiftStartDate;
	}

	public void setShiftStartDate(String shiftStartDate) {
		this.shiftStartDate = shiftStartDate;
	}

	public String getShiftStartTime() {
		return shiftStartTime;
	}

	public void setShiftStartTime(String shiftStartTime) {
		this.shiftStartTime = shiftStartTime;
	}

	public String getShiftEndDate() {
		return shiftEndDate;
	}

	public void setShiftEndDate(String shiftEndDate) {
		this.shiftEndDate = shiftEndDate;
	}

	public String getShiftEndTime() {
		return shiftEndTime;
	}

	public void setShiftEndTime(String shiftEndTime) {
		this.shiftEndTime = shiftEndTime;
	}

	public String getAllowedBreaks() {
		return allowedBreaks;
	}

	public void setAllowedBreaks(String allowedBreaks) {
		this.allowedBreaks = allowedBreaks;
	}

	@Override
	public String toString() {
		return String.format(
				"ShiftScheduleBean [shiftStartDate=%s, shiftStartTime=%s, shiftEndDate=%s, shiftEndTime=%s, allowedBreaks=%s]",
				shiftStartDate, shiftStartTime, shiftEndDate, shiftEndTime, allowedBreaks);
	}


}
