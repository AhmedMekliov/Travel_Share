package com.spring.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.dao.ChangeUserDetailsDao;

@Service
public class ChangeUserDetailsServiceImpl implements ChangeUserDetailsService {
	@Autowired
	ChangeUserDetailsDao changeUserDetailsDao;

	@Override
	public int checkPassword(String email, String oldPass) {
		return changeUserDetailsDao.checkPassword(email, oldPass);

	}

	@Override
	public void changePasswordByEmail(String email, String newPass) {
		changeUserDetailsDao.changePasswordByEmail(email, newPass);

	}

	@Override
	public void chagePhonenumberByEmail(String email, String phonenumber) {
		changeUserDetailsDao.chagePhonenumberByEmail(email, phonenumber);

	}

	@Override
	public int checkMail(String email) {

		return changeUserDetailsDao.checkMail(email);
	}

}
