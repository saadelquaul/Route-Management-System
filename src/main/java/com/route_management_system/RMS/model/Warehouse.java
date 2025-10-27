package com.route_management_system.RMS.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@Data
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

    @Column(nullable = true)
    private Double latitude;

    @Column(nullable = false, name = "opening_date")
    private LocalTime openingDate;

    @Column(nullable = false, name = "closing_date")
    private LocalTime closingDate;

}
