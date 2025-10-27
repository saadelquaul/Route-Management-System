package com.route_management_system.RMS.model;


import com.route_management_system.RMS.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;


    @Column(nullable = false, name = "max_weight_kg")
    private Double maxWeightKg;

    @Column(nullable = false, name = "max_volume_m3")
    private Double maxVolumeM3;


    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    private List<Tour> tours;
}
