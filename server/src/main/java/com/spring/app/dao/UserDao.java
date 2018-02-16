package com.spring.app.dao;

import com.spring.app.model.UserInfo;

public interface UserDao {
	public UserInfo getUser(String username);
	public void registerUser(UserInfo user);
	public UserInfo getUserDetailsByEmail(String email);
}
