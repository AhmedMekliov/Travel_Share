package com.spring.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.dao.SharePostDao;
import com.spring.app.model.Post;

@Service
public class SharePostServiceImpl implements SharePostService{
	
	@Autowired
	SharePostDao sharePostDao;

	@Override
	public void sharePost(Post post) {
		sharePostDao.sharePost(post);
	}

}
