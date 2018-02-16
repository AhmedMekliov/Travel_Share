package com.spring.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.spring.app.model.Place;
import com.spring.app.model.Post;
import com.spring.app.model.UserInfo;

@Repository
public class SearchPostDaoImpl implements SearchPostDao {

	String findPostsQuery = "select * from posts where posts.start_dest = ? and posts.end_dest = ? and posts.start_date = ?";

	protected JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Post> findPosts(String startDest, String endDest,
			String startDate) {
		java.sql.Date sqlStartDate = null;

		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate = formatter.parse(startDate);
			sqlStartDate = new java.sql.Date(myDate.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		final List<Post> postsList = new ArrayList<Post>();

		jdbcTemplate.query(findPostsQuery, new Object[] { startDest, endDest, sqlStartDate },
				new ResultSetExtractor<Post>() {

					@Override
					public Post extractData(ResultSet rs) throws SQLException,
							DataAccessException {
						Post post = null;
						while (rs.next()) {
							post = new Post();
							post.setDate(rs.getString("start_date"));
							post.setDescription(rs.getString("description"));
							post.setEndDestination(rs.getString("end_dest"));
							post.setStartDestination(rs.getString("start_dest"));
							post.setTimeStart(rs.getString("start_time"));
							UserInfo user = new UserInfo();
							user.setEmail(rs.getString("user_email"));
							user.setPhonenumber(rs.getString("user_phone"));
							user.setUsername(rs.getString("username"));
							post.setUserInfo(user);
							postsList.add(post);

						}
						return post;
					}

				});
		return postsList;
	}

	@Override
	public void deletePost(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Post> findPostsByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
