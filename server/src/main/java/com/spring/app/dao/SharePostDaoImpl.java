package com.spring.app.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.app.model.Post;

@Repository
public class SharePostDaoImpl implements SharePostDao {
	
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void sharePost(Post post) {
		String startDateStr = post.getDate();
		String startTimeStr = post.getTimeStart();
		java.sql.Date sqlStartDate = null;
		
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate = formatter.parse(startDateStr);
			sqlStartDate = new java.sql.Date(myDate.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}
				
		StringBuilder insertPostQuery = new StringBuilder();
		insertPostQuery
				.append("insert into posts(username, user_email, user_phone, start_dest, end_dest, start_date, start_time, description)"
						+ "values (");
		insertPostQuery.append("'"+post.getUserInfo().getUsername()+"'");
		insertPostQuery.append(", ");
		insertPostQuery.append("'"+post.getUserInfo().getEmail()+"'");
		insertPostQuery.append(", ");
		insertPostQuery.append("'"+post.getUserInfo().getPhonenumber()+"'");
		insertPostQuery.append(", ");
		insertPostQuery.append("'"+post.getStartDestination()+"'");
		insertPostQuery.append(", ");
		insertPostQuery.append("'"+post.getEndDestination()+"'");
		insertPostQuery.append(", ");
		insertPostQuery.append("'"+sqlStartDate+"'");
		insertPostQuery.append(", ");
		insertPostQuery.append("'"+startTimeStr+"'");
		insertPostQuery.append(", ");
		insertPostQuery.append("'"+post.getDescription()+"'");
		insertPostQuery.append(")");
		this.jdbcTemplate.execute(insertPostQuery.toString());
		
	}

}
