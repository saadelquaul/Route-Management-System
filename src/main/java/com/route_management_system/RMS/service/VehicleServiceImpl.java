package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Vehicle;
import com.route_management_system.RMS.model.dto.VehicleDTO;
import com.route_management_system.RMS.model.mapper.VehicleMapper;
import com.route_management_system.RMS.repository.VehicleRepository;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
public class VehicleServiceImpl implements VehicleService{

    VehicleMapper vehicleMapper;
    VehicleRepository vehicleRepository;


    @Override
    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDTO(savedVehicle);
    }

    @Override
    public VehicleDTO getVehicleById(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(()-> new RuntimeException("Vehicle not found!"));
        return vehicleMapper.toDTO(vehicle);
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream().map(vehicleMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteVehicle(Long vehicleId) {
        if(!vehicleRepository.existsById(vehicleId)) {
            throw new RuntimeException("Vehicle not found");
        }
        vehicleRepository.deleteById(vehicleId);

    }
}
