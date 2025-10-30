package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.Tour;
import com.route_management_system.RMS.model.Vehicle;
import com.route_management_system.RMS.model.Warehouse;
import com.route_management_system.RMS.model.enums.OptimizationAlgorithmType;
import com.route_management_system.RMS.model.enums.TourStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component("clarkeWrightOptimizer")
public class ClarkeWrightOptimizer implements TourOptimizer{


    @Override
    public Tour calculateOptimalTour(Warehouse warehouse, Vehicle vehicle, List<Delivery> pendingDeliveries) {

        final double maxWeight = vehicle.getType().getMaxWeightKg();
        final double maxVolume = vehicle.getType().getMaxVolumeM3();

        List<Delivery> validDeliveries = new ArrayList<>();
        for (Delivery d : pendingDeliveries) {
            if (d.getWeightKg() <= maxWeight && d.getVolumeM3() <= maxVolume) {
                validDeliveries.add(d);
            }
        }

        List<Saving> savings = calculateAllSavings(validDeliveries);


        Collections.sort(savings);

        Map<Delivery, List<Delivery>> routes = new HashMap<>();
        Map<List<Delivery>, RouteMetrics> metrics = new HashMap<>();

        for (Delivery d : validDeliveries) {

                List<Delivery> route = new ArrayList<>();
                route.add(d);
                routes.put(d, route);
                metrics.put(route, new RouteMetrics(d.getWeightKg(), d.getVolumeM3()));

        }

        for (Saving s : savings) {
            Delivery from = s.getFrom();
            Delivery to = s.getTo();

            List<Delivery> routeFrom = routes.get(from);
            List<Delivery> routeTo = routes.get(to);

            if (routeFrom == null || routeTo == null || routeFrom == routeTo) {
                continue;
            }

            if (routeFrom.get(routeFrom.size() - 1) == from && routeTo.get(0) == to) {

                RouteMetrics metricsFrom = metrics.get(routeFrom);
                RouteMetrics metricsTo = metrics.get(routeTo);

                if (metricsFrom == null || metricsTo == null) {
                    continue;
                }

                double newWeight = metricsFrom.weight + metricsTo.weight;
                double newVolume = metricsFrom.volume + metricsTo.volume;

                if (newWeight <= maxWeight && newVolume <= maxVolume) {


                    routeFrom.addAll(routeTo);
                    metricsFrom.weight = newWeight;
                    metricsFrom.volume = newVolume;
                    metrics.remove(routeTo);
                    for (Delivery d : routeTo) {
                        routes.put(d, routeFrom);
                    }
                }
            }
        }

        List<Delivery> bestRoute = routes.values().stream()
                .filter(route -> metrics.containsKey(route))
                .max(Comparator.comparing(List::size))
                .orElse(new ArrayList<>());

        double totalDistance = 0.0;
        double currentLat = warehouse.getLatitude();
        double currentLon = warehouse.getLongitude();

        for (Delivery d : bestRoute) {
            totalDistance += calculateDistance(currentLat, currentLon, d.getLatitude(), d.getLongitude());
            currentLat = d.getLatitude();
            currentLon = d.getLongitude();
        }


        totalDistance += calculateDistance(currentLat,currentLon, warehouse.getLatitude(), warehouse.getLongitude());
        return buildTour(warehouse, vehicle, bestRoute, totalDistance);
    }


    private List<Saving> calculateAllSavings(List<Delivery> deliveries) {
        List<Saving> savings  = new ArrayList<>();
        for (int i = 0; i < deliveries.size(); i++ ) {
            for(int j = i + 1; j < deliveries.size(); j++) {
                Delivery d1 = deliveries.get(i);
                Delivery d2 = deliveries.get(j);

                double savingAmount = (d1.getDistanceFromWarehouse() + d2.getDistanceFromWarehouse()) -
                        calculateDistance(d1.getLatitude(), d1.getLongitude(), d2.getLatitude(), d2.getLongitude());
                if(savingAmount > 0) {
                    savings.add(new Saving(d1, d2, savingAmount));
                    savings.add(new Saving(d2, d1, savingAmount));
                }
            }
        }
        return savings;
    }

    private static class RouteMetrics {
        double weight;
        double volume;

        RouteMetrics(double weight, double volume) {
            this.weight = weight;
            this.volume = volume;
        }
    }
    private Tour buildTour(Warehouse warehouse, Vehicle vehicle, List<Delivery> orderedDeliveries, double totalDistance) {
        Tour tour = new Tour();
        tour.setWarehouse(warehouse);
        tour.setVehicle(vehicle);
        tour.setDeliveries(orderedDeliveries);
        tour.setTotalDistanceKm(totalDistance);
        tour.setAlgorithmUsed(OptimizationAlgorithmType.CLARKE_WRIGHT);
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

 }
