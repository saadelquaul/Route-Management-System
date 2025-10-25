package com.route_management_system.RMS.model;


import com.route_management_system.RMS.model.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "deliveries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false, name = "weight_kg")
    private Double weightKg;

    @Column(name = "volume_m3", nullable = false)
    private Double volumeM3;

    @Column(name = "distance_from_warehouse", nullable = false)
    private double distanceFromWarehouse;

    @Column(name = "preferred_time_start")
    private LocalTime preferredDeliveryTimeStart;

    @Column(name = "preferred_time_end")
    private LocalTime preferredDeliveryTimeEnd;

    @Column(nullable = false, name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    private Tour tour;

}
