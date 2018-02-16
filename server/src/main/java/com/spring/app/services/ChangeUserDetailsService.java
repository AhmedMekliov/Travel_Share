package com.spring.app.services;

public interface ChangeUserDetailsService {
	int checkPassword(String email, String oldPass);

	void changePasswordByEmail(String email, String newPass);

	void chagePhonenumberByEmail(String email, String phonenumber);

	int checkMail(String email);
}
