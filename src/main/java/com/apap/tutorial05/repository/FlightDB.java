package com.apap.tutorial05.repository;

import com.apap.tutorial05.model.FlightModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightDB extends JpaRepository<FlightModel, Long> {

}
