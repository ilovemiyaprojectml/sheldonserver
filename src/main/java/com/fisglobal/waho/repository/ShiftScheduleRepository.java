package com.fisglobal.waho.repository;

import org.springframework.data.repository.CrudRepository;

import com.fisglobal.waho.model.ShiftSchedule;

public interface ShiftScheduleRepository extends CrudRepository<ShiftSchedule, Integer>, ShiftScheduleRepositoryCustom {

}
