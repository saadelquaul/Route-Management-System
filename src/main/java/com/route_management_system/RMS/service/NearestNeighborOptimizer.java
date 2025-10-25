package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.Tour;
import com.route_management_system.RMS.model.Vehicle;
import com.route_management_system.RMS.model.Warehouse;
import com.route_management_system.RMS.model.enums.OptimizationAlgorithmType;
import com.route_management_system.RMS.model.enums.TourStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NearestNeighborOptimizer implements TourOptimizer{
    @Override
    public Tour calculateOptimalTour(Warehouse warehouse, Vehicle vehicle, List<Delivery> pendingDeliveries) {


        List<Delivery> orderedDeliveriesByDistance = pendingDeliveries.stream().
                sorted(Comparator.comparingDouble(Delivery::getDistanceFromWarehouse))
                .collect(Collectors.toList());

        List<Delivery> tourDeliveries = new ArrayList<>();

        double totalDistance = 0.0;
        double totalWeightKg = 0.0;
        double totalVolumeM3 = 0.0;

        for(Delivery d: orderedDeliveriesByDistance) {
            totalWeightKg += d.getWeightKg();
            totalVolumeM3 += d.getVolumeM3();
            if(totalWeightKg > vehicle.getMaxWeightKg() || totalVolumeM3 > vehicle.getMaxVolumeM3()) {
                break;
            }
            totalDistance += d.getDistanceFromWarehouse();
            tourDeliveries.add(d);
        }

        return buildTour(warehouse, vehicle, tourDeliveries, totalDistance);

    }

    private Tour buildTour(Warehouse warehouse, Vehicle vehicle, List<Delivery> deliveries, double totalDistance) {

        Tour tour  = new Tour();
        tour.setWarehouse(warehouse);
        tour.setVehicle(vehicle);
        tour.setDeliveries(deliveries);
        tour.setTotalDistanceKm(totalDistance);
        tour.setAlgorithmUsed(OptimizationAlgorithmType.NEAREST_NEIGHBOR);
        tour.setStatus(TourStatus.PENDING);
        tour.setDate(LocalDate.now());

        return tour;

    }
}
