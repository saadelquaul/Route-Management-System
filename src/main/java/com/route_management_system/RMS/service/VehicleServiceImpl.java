package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.dto.VehicleDTO;
import com.route_management_system.RMS.model.mapper.VehicleMapper;
import com.route_management_system.RMS.repository.VehicleRepository;
import lombok.Setter;

import java.util.List;

@Setter
public class VehicleServiceImpl implements VehicleService{

    VehicleMapper vehicleMapper;
    VehicleRepository vehicleRepository;
    @Override
    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        return null;
    }

    @Override
    public VehicleDTO getVehicleById(Long vehicleId) {
        return null;
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return List.of();
    }

    @Override
    public void deleteVehicle(Long vehicleId) {

    }
}
