package com.fisglobal.waho.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fisglobal.waho.services.WahoBatchJobService;

@Entity
@Table(name="wh_user_shift_schedules")
public class ShiftSchedule {
    
    static Logger log = LoggerFactory.getLogger(ShiftSchedule.class);
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int shftSchedId;
    private LocalDateTime schedStartDate;
    private LocalDateTime schedEndDate;
    private LocalDateTime actualEndDate;
    private LocalDateTime dateCreated;
    private int remainingBreakTime;
    private LocalDateTime dateLastUpdated;
    private int createdBy;
    private int lastUpdatedBy;
    private String isActive;

    @Transient
    public String isShiftEnded;
    
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    
    @JsonIgnore
    @OneToMany(mappedBy = "shiftSchedule", cascade=CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ShiftScheduleTime> shiftScheduleTimes;
    
    public ShiftSchedule() {
        // TODO Auto-generated constructor stub
    }
    
    public ShiftSchedule(LocalDateTime schedStartDate, LocalDateTime schedEndDate, LocalDateTime actualEndDate,
            LocalDateTime dateCreated, int remainingBreakTime, LocalDateTime dateLastUpdated, int createdBy, int lastUpdatedBy,
            String isActive) {
        super();
        this.schedStartDate = schedStartDate;
        this.schedEndDate = schedEndDate;
        this.actualEndDate = actualEndDate;
        this.dateCreated = dateCreated;
        this.remainingBreakTime = remainingBreakTime; 
        this.dateLastUpdated = dateLastUpdated;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.isActive = isActive;
    }
    
    /*public ShiftSchedule(LocalDateTime schedStartDate, LocalDateTime schedEndDate, LocalDateTime actualEndDate,
            LocalDateTime dateCreated, LocalDateTime dateLastUpdated, int createdBy, int lastUpdatedBy,
            String isActive) {
        super();
        this.schedStartDate = schedStartDate;
        this.schedEndDate = schedEndDate;
        this.actualEndDate = actualEndDate;
        this.dateCreated = dateCreated;
        this.remainingBreakTime = 5400; 
        this.dateLastUpdated = dateLastUpdated;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.isActive = isActive;
    }
    */
    public int getShftSchedId() {
        return shftSchedId;
    }
    public void setShftSchedId(int shftSchedId) {
        this.shftSchedId = shftSchedId;
    }
    public LocalDateTime getSchedStartDate() {
        return schedStartDate;
    }
    public void setSchedStartDate(LocalDateTime schedStartDate) {
        this.schedStartDate = schedStartDate;
    }
    public LocalDateTime getSchedEndDate() {
        return schedEndDate;
    }
    public void setSchedEndDate(LocalDateTime schedEndDate) {
        this.schedEndDate = schedEndDate;
    }
    public LocalDateTime getActualEndDate() {
        return actualEndDate;
    }
    public void setActualEndDate(LocalDateTime actualEndDate) {
        this.actualEndDate = actualEndDate;
    }
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
    public int getRemainingBreakTime() {
		return remainingBreakTime;
	}

	public void setRemainingBreakTime(int remainingBreakTime) {
		this.remainingBreakTime = remainingBreakTime;
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

    public List<ShiftScheduleTime> getShiftScheduleTimes() {
        return shiftScheduleTimes;
    }

    public void setShiftScheduleTimes(List<ShiftScheduleTime> shiftScheduleTimes) {
        this.shiftScheduleTimes = shiftScheduleTimes;
    }

    @Override
	public String toString() {
		return String.format(
				"ShiftSchedule [shftSchedId=%s, schedStartDate=%s, schedEndDate=%s, actualEndDate=%s, dateCreated=%s, remainingBreakTime=%s, dateLastUpdated=%s, createdBy=%s, lastUpdatedBy=%s, isActive=%s, isShiftEnded=%s, user=%s, shiftScheduleTimes=%s]",
				shftSchedId, schedStartDate, schedEndDate, actualEndDate, dateCreated, remainingBreakTime,
				dateLastUpdated, createdBy, lastUpdatedBy, isActive, isShiftEnded, user, shiftScheduleTimes);
	}

	/**
     * 
     * Used to send isShiftEnded information to front-end.
     * Used to check if shift is already past scheduled End Date
     * This is set to true if current @ShiftSchedule is after after schedEndDate.
     * This is set to false if @ShiftSchedule is undertime.  
     *  
     */
    public void setIsShiftEnded() {
        log.debug("setIsShiftEnded() - Start");
        
        boolean isShiftEndedBool = LocalDateTime.now().isAfter(schedEndDate);
        this.isShiftEnded = isShiftEndedBool ? "Y" : "N";
        
        log.debug(String.format("setIsShiftEnded() - [%s]", isShiftEnded));
        log.debug("setIsShiftEnded() - End");
    }

    
    /**
     * 
     * Local procedure to check if shift is already past scheduled End Date
     * 
     * @return boolean true if current @ShiftSchedule is after after schedEndDate.
     *                 false if @ShiftSchedule is undertime.  
     * 
     */
    public boolean hasShiftEnded() {
        return LocalDateTime.now().isAfter(schedEndDate);
    }

    
    /**
     * 
     * Local procedure to check if shift is completed
     * 
     */
    public boolean isShiftComplete() {
        return actualEndDate != null;
    }
    
}
