package com.route_management_system.RMS.model;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;


@Getter
@Setter
@Entity
@Table(name = "warehouses")
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false, name = "opening_time")
    private LocalTime openingTime;

    @Column(nullable = false, name = "closing_time")
    private LocalTime closingTime;

}
