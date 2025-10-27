package com.route_management_system.RMS.model.dto;


import com.route_management_system.RMS.model.enums.VehicleType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleDTO {

    private Long id;

    private VehicleType type;

    private Double maxWeightKg;
    private Double maxVolumeM3;
}
