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



}
