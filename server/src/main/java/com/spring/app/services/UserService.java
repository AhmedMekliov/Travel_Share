package com.spring.app.services;

import com.spring.app.model.UserInfo;


public interface UserService {
	public void registerUser(UserInfo user);
	public UserInfo getUserDetailsByEmail(String email);
}
