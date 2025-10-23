package com.route_management_system.RMS.model;


import com.route_management_system.RMS.model.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(nullable = false, name = "delivery_time")
    private LocalDateTime deliveryTime;

    @Column(nullable = false, name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

}
