package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.Tour;
import com.route_management_system.RMS.model.Vehicle;
import com.route_management_system.RMS.model.Warehouse;
import com.route_management_system.RMS.model.dto.TourDTO;
import com.route_management_system.RMS.model.enums.DeliveryStatus;
import com.route_management_system.RMS.model.enums.VehicleType;
import com.route_management_system.RMS.model.mapper.TourMapper;
import com.route_management_system.RMS.repository.DeliveryRepository;
import com.route_management_system.RMS.repository.TourRepository;
import com.route_management_system.RMS.repository.VehicleRepository;
import com.route_management_system.RMS.repository.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TourServiceImplTest {

    @Mock
    private TourRepository tourRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private TourMapper tourMapper;

    @Mock
    private TourOptimizer nearestNeighborOptimizer;

    @Mock
    private TourOptimizer clarkeWrightOptimizer;

    private TourServiceImpl tourService;

    private Warehouse warehouse;
    private Vehicle vehicle;
    private Delivery delivery1;
    private Delivery delivery2;
    private Tour tour;
    private TourDTO tourDTO;

    @BeforeEach
    void setUp() {
        // Manually create TourServiceImpl with mocked dependencies
        tourService = new TourServiceImpl(
            tourRepository,
            vehicleRepository,
            warehouseRepository,
            deliveryRepository,
            tourMapper,
            nearestNeighborOptimizer,
            clarkeWrightOptimizer
        );

        warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setLatitude(33.5731);
        warehouse.setLongitude(-7.5898);
        warehouse.setOpeningTime(LocalTime.of(8, 0));
        warehouse.setClosingTime(LocalTime.of(18, 0));

        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setType(VehicleType.TRUCK);
        vehicle.setMaxWeightKg(1000.0);
        vehicle.setMaxVolumeM3(20.0);

        delivery1 = new Delivery();
        delivery1.setId(1L);
        delivery1.setLatitude(33.5800);
        delivery1.setLongitude(-7.5900);
        delivery1.setWeightKg(50.0);
        delivery1.setVolumeM3(2.0);
        delivery1.setStatus(DeliveryStatus.PENDING);
        delivery1.setDistanceFromWarehouse(5.0);

        delivery2 = new Delivery();
        delivery2.setId(2L);
        delivery2.setLatitude(33.5900);
        delivery2.setLongitude(-7.6000);
        delivery2.setWeightKg(30.0);
        delivery2.setVolumeM3(1.5);
        delivery2.setStatus(DeliveryStatus.PENDING);
        delivery2.setDistanceFromWarehouse(10.0);

        tour = new Tour();
        tour.setId(1L);
        tour.setWarehouse(warehouse);
        tour.setVehicle(vehicle);
        tour.setDeliveries(Arrays.asList(delivery1, delivery2));
        tour.setTotalDistanceKm(15.5);

        tourDTO = new TourDTO();
        tourDTO.setId(1L);
        tourDTO.setWarehouseId(1L);
        tourDTO.setTotalDistanceKm(15.5);
    }


    @Test
    void getOptimizedTour_ShouldCreateAndSaveTour() {
        // Arrange
        List<Delivery> pendingDeliveries = Arrays.asList(delivery1, delivery2);

        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(deliveryRepository.findByStatus(DeliveryStatus.PENDING)).thenReturn(pendingDeliveries);
        when(nearestNeighborOptimizer.calculateOptimalTour(warehouse, vehicle, pendingDeliveries)).thenReturn(tour);
        when(tourRepository.save(tour)).thenReturn(tour);
        when(tourMapper.toDto(tour)).thenReturn(tourDTO);
        when(deliveryRepository.saveAll(any())).thenReturn(pendingDeliveries);

        // Act
        TourDTO result = tourService.getOptimizedTour(1L, 1L, "NEAREST_NEIGHBOR");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(tourRepository, times(1)).save(tour);
        verify(deliveryRepository, times(1)).saveAll(any());
        verify(nearestNeighborOptimizer, times(1)).calculateOptimalTour(warehouse, vehicle, pendingDeliveries);
    }

    @Test
    void getOptimizedTour_WhenWarehouseNotFound_ShouldThrowException() {
        // Arrange
        when(warehouseRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
            () -> tourService.getOptimizedTour(99L, 1L, "NEAREST_NEIGHBOR"));
        verify(warehouseRepository, times(1)).findById(99L);
        verify(tourRepository, never()).save(any());
    }

    @Test
    void getOptimizedTour_WhenVehicleNotFound_ShouldThrowException() {
        // Arrange
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
            () -> tourService.getOptimizedTour(1L, 99L, "NEAREST_NEIGHBOR"));
        verify(vehicleRepository, times(1)).findById(99L);
        verify(tourRepository, never()).save(any());
    }

    @Test
    void getTotalDistance_ShouldReturnTourDistance() {
        // Arrange
        when(tourRepository.findById(1L)).thenReturn(Optional.of(tour));

        // Act
        double distance = tourService.getTotalDistance(1L);

        // Assert
        assertEquals(15.5, distance);
        verify(tourRepository, times(1)).findById(1L);
    }

    @Test
    void getTotalDistance_WhenTourNotFound_ShouldThrowException() {
        // Arrange
        when(tourRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
            () -> tourService.getTotalDistance(99L));
    }

    @Test
    void findById_WhenExists_ShouldReturnTour() {
        // Arrange
        when(tourRepository.findById(1L)).thenReturn(Optional.of(tour));
        when(tourMapper.toDto(tour)).thenReturn(tourDTO);

        // Act
        TourDTO result = tourService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(tourRepository, times(1)).findById(1L);
        verify(tourMapper, times(1)).toDto(tour);
    }

    @Test
    void findById_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(tourRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
            () -> tourService.findById(99L));
        verify(tourRepository, times(1)).findById(99L);
    }
}

