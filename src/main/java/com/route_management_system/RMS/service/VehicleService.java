package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.dto.VehicleDTO;

import java.util.List;

public interface VehicleService {
    VehicleDTO createVehicle(VehicleDTO vehicleDTO);
    VehicleDTO getVehicleById(Long vehicleId);
    List<VehicleDTO> getAllVehicles();

    void deleteVehicle(Long vehicleId);

}
