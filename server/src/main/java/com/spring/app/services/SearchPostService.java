package com.spring.app.services;

import java.util.List;

import com.spring.app.model.Post;

public interface SearchPostService {
	List<Post> findPosts(String startDest, String endDest, String startDate);
	void deletePost(int id);
	List<Post> findPostsByEmail(String email);
}
