package com.fisglobal.waho.repository;

import org.springframework.data.repository.CrudRepository;

import com.fisglobal.waho.model.NotificationQueue;

public interface NotificationQueueRepository extends CrudRepository<NotificationQueue, Integer>, NotificationQueueRepositoryCustom {

}
