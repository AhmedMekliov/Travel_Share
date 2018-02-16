package com.spring.app.dao;

import java.util.List;

import com.spring.app.model.Post;

public interface SearchPostDao {
	List<Post> findPosts(String startDest, String endDest, String startDate);

	void deletePost(int id);

	List<Post> findPostsByEmail(String email);
}
