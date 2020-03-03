package com.apap.tutorial05.service;

import com.apap.tutorial05.model.PilotModel;

public interface PilotService {
PilotModel getPilotDetailByLicenseNumber(String licensenumber);
void addPilot(PilotModel pilot);
PilotModel getPilotDetailByID(Long id);
void delete(PilotModel pilot);
}
