package com.apap.tutorial05.service;

import com.apap.tutorial05.model.FlightModel;

public interface FlightService {
	void addFlight(FlightModel flight);
	void deleteFlight(FlightModel flightModel);
	void deleteFlightbyId(Long id);
	FlightModel getById(Long id);
	
}
