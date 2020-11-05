package com.fisglobal.waho.repository;

import java.util.List;

import com.fisglobal.waho.model.User;

public interface UserRepositoryCustom {
	List<User> getUsersByUserParentId(int userParentId);
	User getUsersByUserEid(String userEid);
}
