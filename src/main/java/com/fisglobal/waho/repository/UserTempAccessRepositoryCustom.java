package com.fisglobal.waho.repository;

import com.fisglobal.waho.model.UserTempAccess;

public interface UserTempAccessRepositoryCustom {
	UserTempAccess getUsersByUserEid(String userEid);

	int resetPassword(String tempPassword, String email);

	int changePassword(String tempPassword, String userEid, String password, String type);

	UserTempAccess getUsersByUserId(int user_id);
}
