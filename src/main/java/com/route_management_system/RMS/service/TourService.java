package com.route_management_system.RMS.service;


import com.route_management_system.RMS.model.Tour;

import com.route_management_system.RMS.model.dto.TourDTO;

import java.util.List;

public interface TourService {

    TourDTO getOptimizedTour(Long warehouseId, Long vehicleId, String algorithmUsedName);
    double getTotalDistance(Long tourId);
    TourDTO findById(Long tourId);
    boolean deleteTour(Long tourId);
    List<Tour> getAllTours();
    TourDTO getOptimizedTourByClarkeAndWright(Long warehouseId, Long vehicleId);
}
