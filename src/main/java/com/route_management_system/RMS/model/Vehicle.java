package com.route_management_system.RMS.model;


import com.route_management_system.RMS.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType type;


    @Column(nullable = false, name = "max_weight_kg")
    private Double maxWeightKg;

    @Column(nullable = false, name = "max_volume_m3")
    private Double maxVolumeM3;


    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    private List<Tour> tours;
}
