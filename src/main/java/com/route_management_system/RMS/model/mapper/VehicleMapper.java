package com.route_management_system.RMS.model.mapper;

import com.route_management_system.RMS.model.Vehicle;
import com.route_management_system.RMS.model.dto.VehicleDTO;

public class VehicleMapper {

    public VehicleDTO toDTO(Vehicle vehicle) {
        if(vehicle == null) {
            return null;
        }

        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setType(vehicle.getType());
        dto.setMaxWeightKg(vehicle.getMaxWeightKg());
        dto.setMaxVolumeM3(vehicle.getMaxVolumeM3());

        return dto;
    }

    public Vehicle toEntity(VehicleDTO dto) {
        if(dto == null) {
            return null;
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setType(dto.getType());
        vehicle.setMaxWeightKg(dto.getMaxWeightKg());
        vehicle.setMaxVolumeM3(dto.getMaxVolumeM3());
        return vehicle;
    }
}
