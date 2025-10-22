package com.route_management_system.RMS.model;


import com.route_management_system.RMS.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    VehicleType vehicleType;

    @Column(nullable = false)
    Double maxWeightKg;

    @Column(nullable = false)
    Double maxVolumeM3;
}
