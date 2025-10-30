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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourServiceImpl implements TourService{

    private final TourRepository tourRepository;
    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;
    private final DeliveryRepository deliveryRepository;
    private final TourMapper tourMapper;
    private final TourOptimizer nearestNeighborOptimizer;
    private final TourOptimizer clarkeWrightOptimizer;

    public TourServiceImpl(
            TourRepository tourRepository,
            VehicleRepository vehicleRepository,
            WarehouseRepository warehouseRepository,
            DeliveryRepository deliveryRepository,
            TourMapper tourMapper,
            @Qualifier("nearestNeighborOptimizer") TourOptimizer nearestNeighborOptimizer,
            @Qualifier("clarkeWrightOptimizer") TourOptimizer clarkeWrightOptimizer) {
        this.tourRepository = tourRepository;
        this.vehicleRepository = vehicleRepository;
        this.warehouseRepository = warehouseRepository;
        this.deliveryRepository = deliveryRepository;
        this.tourMapper = tourMapper;
        this.nearestNeighborOptimizer = nearestNeighborOptimizer;
        this.clarkeWrightOptimizer = clarkeWrightOptimizer;
    }









    @Override
    public TourDTO getOptimizedTour(Long warehouseId, Long vehicleId, String algorithmUsedName) {


        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() -> new RuntimeException("Warehouse not found!"));
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new RuntimeException("Vehicle not found!"));
        List<Delivery> pendingDeliveries = deliveryRepository.findByStatus(DeliveryStatus.PENDING);


        TourOptimizer chosenOptimizer;
        if ("CLARKE_WRIGHT".equalsIgnoreCase(algorithmUsedName)) {
            chosenOptimizer = clarkeWrightOptimizer;
        } else if ("NEAREST_NEIGHBOR".equalsIgnoreCase(algorithmUsedName)) {
            chosenOptimizer = nearestNeighborOptimizer;
        } else {
            chosenOptimizer = clarkeWrightOptimizer;
        }

        Tour optimizedTour = chosenOptimizer.calculateOptimalTour(warehouse, vehicle, pendingDeliveries);
        Tour savedTour = tourRepository.save(optimizedTour);

        List<Delivery> deliveries = optimizedTour.getDeliveries();
        deliveries.forEach(d -> {
            d.setStatus(DeliveryStatus.IN_TRANSIT);
            d.setTour(savedTour);
        });
        deliveryRepository.saveAll(deliveries);

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
    public boolean deleteTour(Long tourId) {
        if(!tourRepository.existsById(tourId)) {
            throw new RuntimeException("Tour not found!");

        }
        tourRepository.deleteById(tourId);
        return true;
    }

    @Override
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    @Override
    public TourDTO getOptimizedTourByClarkeAndWright(Long warehouseId, Long vehicleId) {

        return null;
    }


}
