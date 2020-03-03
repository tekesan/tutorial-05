package com.apap.tutorial05.repository;

import com.apap.tutorial05.model.PilotModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotDB extends JpaRepository<PilotModel, Long> {
	PilotModel findByLicenseNumber(String licensenumber);
	
}
