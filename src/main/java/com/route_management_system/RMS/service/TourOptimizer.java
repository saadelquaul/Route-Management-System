package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.Tour;
import com.route_management_system.RMS.model.Vehicle;
import com.route_management_system.RMS.model.Warehouse;

import java.util.List;

public interface TourOptimizer {

    Tour calculateOptimalTour(Warehouse warehouse, Vehicle vehicle, List<Delivery> pendingDeliveries);
}
