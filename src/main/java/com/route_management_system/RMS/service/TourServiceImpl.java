package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.Tour;
import com.route_management_system.RMS.model.Vehicle;
import com.route_management_system.RMS.model.Warehouse;
import com.route_management_system.RMS.model.dto.TourDTO;
import com.route_management_system.RMS.model.enums.DeliveryStatus;
import com.route_management_system.RMS.model.mapper.TourMapper;
import com.route_management_system.RMS.repository.DeliveryRepository;
import com.route_management_system.RMS.repository.TourRepository;
import com.route_management_system.RMS.repository.VehicleRepository;
import com.route_management_system.RMS.repository.WarehouseRepository;

import java.io.DataInput;
import java.util.List;

public class TourServiceImpl implements TourService{

    private TourRepository tourRepository;
    private VehicleRepository vehicleRepository;
    private WarehouseRepository warehouseRepository;
    private DeliveryRepository deliveryRepository;
    private TourMapper tourMapper;
    private TourOptimizer tourOptimizer;








    @Override
    public double calculateDistance(double warehouseLatitude, double warehouseLongitude, double deliveryLatitude, double deliveryLongitude) {
        final int R = 6371;

        double latDistance = Math.toRadians(deliveryLatitude - warehouseLatitude);
        double lonDistance = Math.toRadians(deliveryLongitude - warehouseLongitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(warehouseLatitude)) * Math.cos(Math.toRadians(deliveryLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    @Override
    public TourDTO getOptimizedTour(Long warehouseId, Long vehicleId) {

        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() -> new RuntimeException("Warehouse not found!"));
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new RuntimeException("Vehicle not found!"));
        List<Delivery> pendingDeliveries = deliveryRepository.findByStatus(DeliveryStatus.PENDING);

        Tour optimizedTour = tourOptimizer.calculateOptimalTour(warehouse, vehicle, pendingDeliveries);
        Tour savedTour = tourRepository.save(optimizedTour);

        return tourMapper.toDto(savedTour);
    }

    @Override
    public double getTotalDistance(Long tourId) {
        return tourRepository.findById(tourId).orElseThrow(() -> new RuntimeException("Tour not found!")).getTotalDistanceKm();
    }

    @Override
    public TourDTO findById(Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found!"));

        return tourMapper.toDto(tour);
    }

    @Override
    public void deleteTour(Long tourId) {
        if(!tourRepository.existsById(tourId)) {
            throw new RuntimeException("Tour not found!");
        }
        tourRepository.deleteById(tourId);
    }

    public void setTourRepository(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void setWarehouseRepository(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }
    public void setDeliveryRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }
    public void setTourMapper(TourMapper tourMapper) {
        this.tourMapper = tourMapper;
    }
    public void setTourOptimizer(TourOptimizer tourOptimizer) {
        this.tourOptimizer = tourOptimizer;
    }
}
