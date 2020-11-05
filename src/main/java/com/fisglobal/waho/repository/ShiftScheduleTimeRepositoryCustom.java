package com.fisglobal.waho.repository;

import java.util.List;

import com.fisglobal.waho.model.ShiftSchedule;
import com.fisglobal.waho.model.ShiftScheduleTime;

public interface ShiftScheduleTimeRepositoryCustom {
	List<ShiftScheduleTime> findByShiftSchedule(ShiftSchedule shiftSchedule);
	List<ShiftScheduleTime> findMissedTimeByShiftSchedId(Integer shiftSchedId);
	ShiftScheduleTime findCurrentShiftScheduleTimeByShiftSchedId(Integer shiftSchedId);
	List<ShiftScheduleTime> findPendingAndExpiredSchedTime();
}
