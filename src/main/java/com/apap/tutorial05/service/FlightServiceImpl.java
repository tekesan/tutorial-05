package com.apap.tutorial05.service;

import com.apap.tutorial05.model.FlightModel;
import com.apap.tutorial05.repository.FlightDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl implements FlightService {
	@Autowired
	private FlightDB flightDB;

	@Override
	public void addFlight(FlightModel flight) {
		flightDB.save(flight);
	}

	@Override
	public void deleteFlight(FlightModel flightModel) {
        flightDB.delete(flightModel);
		
	}

	@Override
	public void deleteFlightbyId(Long id) {
		flightDB.deleteById(id);
	}

	@Override
	public FlightModel getById(Long id) {
		// TODO Auto-generated method stub
		return flightDB.findById(id).get();	
		}
}
