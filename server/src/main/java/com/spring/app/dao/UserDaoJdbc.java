package com.spring.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.spring.app.model.UserInfo;

@Repository
public class UserDaoJdbc implements UserDao {
	
	protected JdbcTemplate jdbcTemplate;
	String query = "select * from users join user_roles on(users.email = user_roles.email) where users.email = ?";
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public UserInfo getUser(String email) {
		UserInfo user = jdbcTemplate.queryForObject(query, new Object[] {email}, new RowMapper<UserInfo>(){

			@Override
			public UserInfo mapRow(ResultSet result, int arg1) throws SQLException {
				UserInfo user = new UserInfo();
				user.setUsername(result.getString("username"));
				user.setEmail(result.getString("email"));
				String password = result.getString("password");
				password = password.replaceAll("\\s+","");
				user.setPassword(password);
				user.setPhonenumber(result.getString("phonenumber"));
				user.setRole(result.getString("role"));
				return user;
			}
			
		});
		return user;
	}

	@Override
	public void registerUser(UserInfo user) {
		StringBuilder query1 = new StringBuilder();
		query1.append("insert into users (username,password,email,deleteflag,phonenumber) values(");
		query1.append("'"+user.getUsername()+"'");
		query1.append(", ");
		query1.append("'"+user.getPassword()+"'");
		query1.append(", ");
		query1.append("'"+user.getEmail()+"'");
		query1.append(", ");
		query1.append("0");
		query1.append(", ");
		query1.append("'"+user.getPhonenumber()+"'");
		query1.append(")");
		jdbcTemplate.execute(query1.toString());
		
		StringBuilder query2 = new StringBuilder();
		query2.append("insert into user_roles (email,role) values(");
		query2.append("'"+user.getEmail()+"'");
		query2.append(", ");
		query2.append("'"+"ROLE_USER"+"'");
		query2.append(")");
		jdbcTemplate.execute(query2.toString());
		
	}

	@Override
	public UserInfo getUserDetailsByEmail(String email) {
		String query = "select * from users where users.email = ?";
		UserInfo user = new UserInfo();
		
	    user = jdbcTemplate.queryForObject(query, new Object[] {email}, new RowMapper<UserInfo>(){

			@Override
			public UserInfo mapRow(ResultSet result, int arg1) throws SQLException {
				UserInfo user = new UserInfo();
				user.setUsername(result.getString("username"));
				user.setEmail(result.getString("email"));
				user.setPhonenumber(result.getString("phonenumber"));
				return user;
			}
			
		});
		return user;
	}


}
