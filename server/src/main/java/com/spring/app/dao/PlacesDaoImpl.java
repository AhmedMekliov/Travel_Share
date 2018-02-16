package com.spring.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.spring.app.model.Place;

@Repository
public class PlacesDaoImpl implements PlacesDao {
	private String getAllPlacesQuery = "select * from places";

	protected JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Place> getAllPlaces() {
		final List<Place> placesList = new ArrayList<Place>();

		jdbcTemplate.query(getAllPlacesQuery, new ResultSetExtractor<Place>() {

			@Override
			public Place extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				Place place = null;
				while (rs.next()) {
					place = new Place();
					place.setPlaceName(rs.getString("PlaceName"));
					placesList.add(place);

				}
				return place;
			}

		});
		return placesList;
	}

}
