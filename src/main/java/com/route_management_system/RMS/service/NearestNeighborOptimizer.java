package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.Tour;
import com.route_management_system.RMS.model.Vehicle;
import com.route_management_system.RMS.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborOptimizer implements TourOptimizer{
    @Override
    public Tour calculateOptimalTour(Warehouse warehouse, Vehicle vehicle, List<Delivery> pendingDeliveries) {


        List<Delivery> orderedDeliveries = new ArrayList<>();
        List<Delivery> remainingDeliveries = new ArrayList<>(pendingDeliveries);

        double totalDistance = 0.0;

        double currentLat = warehouse.getLatitude();
        double currentLong = warehouse.getLongitude();

        while(!remainingDeliveries.isEmpty()) {


            Delivery closest = findClosestDelivery(currentLat, currentLong, remainingDeliveries);

        }
        return null;
    }

    private Delivery findClosestDelivery(double latitude, double longitude, List<Delivery> deliveries) {
        Delivery closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Delivery d: deliveries) {
            double distance = calculateDistance(latitude,longitude,d.getLatitude(), d.getLongitude()) {
                if(distance < minDistance) {
                    minDistance = distance;
                    closest = d;
                }
            }
        }
        return closest;
    }

}
