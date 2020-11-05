package com.fisglobal.waho.repository;

import java.util.List;

import com.fisglobal.waho.model.ShiftSchedule;
import com.fisglobal.waho.model.User;

public interface ShiftScheduleRepositoryCustom {
	List<ShiftSchedule> findByUser(User user);
	ShiftSchedule getCurrentShiftScheduleByUser(int userId);
	
	ShiftSchedule getPreviousShiftScheduleByUser(int userId); 
}
