package com.route_management_system.RMS.model;


import com.route_management_system.RMS.model.enums.OptimizationAlgorithmType;
import com.route_management_system.RMS.model.enums.TourStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tours")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "assigned_date")
    private LocalDate date;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderColumn(name = "delivery_sequence")
    private List<Delivery> deliveries;

    @Enumerated(EnumType.STRING)
    private TourStatus status;

    @Column(name = "algorithm_used")
    @Enumerated(EnumType.STRING)
    private OptimizationAlgorithmType algorithmUsed;

    @Column(name = "total_distance_km")
    private Double totalDistanceKm;

}
