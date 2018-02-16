package com.spring.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.dao.PlacesDao;
import com.spring.app.model.Place;

@Service
public class PlacesServiceImpl implements PlacesService {
	@Autowired
	PlacesDao placesDao;

	@Override
	public List<Place> getAllPlaces() {

		return placesDao.getAllPlaces();
	}

}
