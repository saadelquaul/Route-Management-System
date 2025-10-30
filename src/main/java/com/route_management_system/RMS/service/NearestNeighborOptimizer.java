package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.Tour;
import com.route_management_system.RMS.model.Vehicle;
import com.route_management_system.RMS.model.Warehouse;
import com.route_management_system.RMS.model.enums.OptimizationAlgorithmType;
import com.route_management_system.RMS.model.enums.TourStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component("nearestNeighborOptimizer")
public class NearestNeighborOptimizer implements TourOptimizer{
    @Override
    public Tour calculateOptimalTour(Warehouse warehouse, Vehicle vehicle, List<Delivery> pendingDeliveries) {

        final double maxWeight = vehicle.getType().getMaxWeightKg();
        final double maxVolume = vehicle.getType().getMaxVolumeM3();

        List<Delivery> availableDeliveries = new ArrayList<>(pendingDeliveries);
        List<Delivery> orderedTour = new ArrayList<>();


        double currentWeight = 0.0;
        double currentVolume = 0.0;
        double totalDistance = 0.0;


        double currentLat = warehouse.getLatitude();
        double currentLon = warehouse.getLongitude();

        while (true) {
            Delivery closest = findClosestDelivery(currentLat, currentLon, availableDeliveries);

            if (closest == null) {
                break;
            }



            double nextWeight = currentWeight + closest.getWeightKg();
            double nextVolume = currentVolume + closest.getVolumeM3();

            if (nextWeight <= maxWeight && nextVolume <= maxVolume) {


                totalDistance += calculateDistance(currentLat, currentLon, closest.getLatitude(), closest.getLongitude());


                orderedTour.add(closest);


                currentLat = closest.getLatitude();
                currentLon = closest.getLongitude();


                currentWeight = nextWeight;
                currentVolume = nextVolume;


                availableDeliveries.remove(closest);

            } else {


                availableDeliveries.remove(closest);

            }
        }

        totalDistance += calculateDistance(
                currentLat, currentLon,
                warehouse.getLatitude(), warehouse.getLongitude()
        );

        return buildTour(warehouse, vehicle, orderedTour, totalDistance);
    }


    private Tour buildTour(Warehouse warehouse, Vehicle vehicle, List<Delivery> deliveries, double totalDistance) {

        Tour tour  = new Tour();
        tour.setWarehouse(warehouse);
        tour.setVehicle(vehicle);
        tour.setDeliveries(deliveries);
        tour.setTotalDistanceKm(totalDistance);
        tour.setAlgorithmUsed(OptimizationAlgorithmType.NEAREST_NEIGHBOR);
        tour.setStatus(TourStatus.PENDING);
        tour.setTourDate(LocalDate.now());

        return tour;

    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of Earth in KM
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private Delivery findClosestDelivery(double currentLat, double currentLon, List<Delivery> remaining) {
        Delivery closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Delivery delivery : remaining) {
            double distance = calculateDistance(
                    currentLat, currentLon,
                    delivery.getLatitude(), delivery.getLongitude()
            );
            if (distance < minDistance) {
                minDistance = distance;
                closest = delivery;
            }
        }
        return closest;
    }
}
