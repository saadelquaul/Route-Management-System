package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.Tour;
import com.route_management_system.RMS.model.Vehicle;
import com.route_management_system.RMS.model.Warehouse;
import com.route_management_system.RMS.model.dto.TourDTO;

import java.util.List;

public interface TourService {

    double calculateDistance(double warehouseLatitude, double warehouseLongitude, double deliveryLatitude, double deliveryLongitude);
    TourDTO getOptimizedTour(Long warehouseId, Long vehicleId);
    double getTotalDistance(Long tourId);
    TourDTO findById(Long tourId);
    boolean deleteTour(Long tourId);
    List<Tour> getAllTours();
    TourDTO getOptimizedTourByClarkeAndWright(Long warehouseId, Long vehicleId);
}
