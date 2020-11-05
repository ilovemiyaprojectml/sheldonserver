package com.fisglobal.waho.repository;

import org.springframework.data.repository.CrudRepository;

import com.fisglobal.waho.model.User;

public interface UserRepository extends CrudRepository<User, Integer>, UserRepositoryCustom{

}
