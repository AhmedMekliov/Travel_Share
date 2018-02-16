package com.spring.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.dao.SearchPostDao;
import com.spring.app.model.Post;

@Service
public class SearchPostServiceImpl implements SearchPostService {

	@Autowired
	SearchPostDao searchPostDao;

	@Override
	public List<Post> findPosts(String startDest, String endDest, String startDate) {
		
		return searchPostDao.findPosts(startDest, endDest, startDate);
	}

	@Override
	public void deletePost(int id) {
		searchPostDao.deletePost(id);
		
	}

	@Override
	public List<Post> findPostsByEmail(String email) {
		return searchPostDao.findPostsByEmail(email);
	}

}
