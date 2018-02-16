package com.spring.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ChangeUserDetailsDaoJDBC implements ChangeUserDetailsDao {

	String checkPasswordQuery = "select * from users where users.email = ? and users.password = ?";
	String checkMailQuery = "select * from users where users.email = ?";
	String changePasswordQuery = "update users set users.password = ? where users.email = ?";
	String changePahonenumberQuery = "update users set users.phonenumber = ? where users.email = ?";

	protected JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int checkPassword(String email, String oldPass) {

		Integer rowNum;
		try {
			rowNum = jdbcTemplate.queryForObject(checkPasswordQuery,
					new Object[] { email, oldPass }, new RowMapper<Integer>() {

						@Override
						public Integer mapRow(ResultSet result, int arg1)
								throws SQLException {
							Integer rowNum = result.getFetchSize();
							return rowNum;
						}

					});
		} catch (EmptyResultDataAccessException e) {
			return 0;
		}
		return rowNum + 1;
	}

	@Override
	public void changePasswordByEmail(String email, String newPass) {
		jdbcTemplate.update(changePasswordQuery,
				new Object[] { newPass, email });

	}

	@Override
	public void chagePhonenumberByEmail(String email, String phonenumber) {
		jdbcTemplate.update(changePahonenumberQuery, new Object[] {
				phonenumber, email });

	}

	@Override
	public int checkMail(String email) {
		Integer rowNum;
		try {
			rowNum = jdbcTemplate.queryForObject(checkMailQuery,
					new Object[] { email }, new RowMapper<Integer>() {

						@Override
						public Integer mapRow(ResultSet result, int arg1)
								throws SQLException {
							Integer rowNum = result.getFetchSize();
							return rowNum;
						}

					});
		} catch (EmptyResultDataAccessException e) {
			return 0;
		}
		return rowNum + 1;
	}

}
