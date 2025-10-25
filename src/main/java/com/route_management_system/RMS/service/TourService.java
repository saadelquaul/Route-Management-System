package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.Tour;
import com.route_management_system.RMS.model.Vehicle;
import com.route_management_system.RMS.model.Warehouse;
import com.route_management_system.RMS.model.dto.TourDTO;

import java.util.List;

public interface TourService {

    double calculateDistance(double warehouseLatitude, double warehouseLongitude, double deliveryLatitude, double deliveryLongitude);
    Tour getOptimizedTour(Warehouse warehouse, Vehicle vehicle, List<Delivery> pendingDeliveries);
    double getTotalDistance(Long tourId);
    TourDTO findById(Long tourId);
    void deleteTour(Long tourId);
}
