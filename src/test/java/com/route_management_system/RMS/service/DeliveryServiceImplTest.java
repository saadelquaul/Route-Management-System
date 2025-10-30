package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.Warehouse;
import com.route_management_system.RMS.model.dto.DeliveryDTO;
import com.route_management_system.RMS.model.enums.DeliveryStatus;
import com.route_management_system.RMS.model.mapper.DeliveryMapper;
import com.route_management_system.RMS.repository.DeliveryRepository;
import com.route_management_system.RMS.repository.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
class DeliveryServiceImplTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliveryMapper deliveryMapper;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private TourService tourService;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    private Delivery delivery;
    private DeliveryDTO deliveryDTO;
    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        warehouse.setId(1L);
        warehouse.setLatitude(33.5731);
        warehouse.setLongitude(-7.5898);
        warehouse.setOpeningTime(LocalTime.of(8, 0));
        warehouse.setClosingTime(LocalTime.of(18, 0));

        delivery = new Delivery();
        delivery.setId(1L);
        delivery.setLatitude(33.5731);
        delivery.setLongitude(-7.5898);
        delivery.setWeightKg(50.0);
        delivery.setVolumeM3(2.0);
        delivery.setDistanceFromWarehouse(10.5);
        delivery.setStatus(DeliveryStatus.PENDING);
        delivery.setPreferredDeliveryTimeStart(LocalTime.of(9, 0));
        delivery.setPreferredDeliveryTimeEnd(LocalTime.of(17, 0));

        deliveryDTO = new DeliveryDTO();
        deliveryDTO.setId(1L);
        deliveryDTO.setWarehouseId(1L);
        deliveryDTO.setLatitude(33.5731);
        deliveryDTO.setLongitude(-7.5898);
        deliveryDTO.setWeightKg(50.0);
        deliveryDTO.setVolumeM3(2.0);
        deliveryDTO.setTotalDistanceFromWarehouse(10.5);
        deliveryDTO.setStatus(DeliveryStatus.PENDING);
    }

    @Test
    void createDelivery_ShouldSetStatusToPending() {
        // Arrange
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(deliveryMapper.toEntity(deliveryDTO)).thenReturn(delivery);
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery);
        when(deliveryMapper.toDto(delivery)).thenReturn(deliveryDTO);

        // Act
        DeliveryDTO result = deliveryService.createDelivery(deliveryDTO);

        // Assert
        assertNotNull(result);
        assertEquals(DeliveryStatus.PENDING, delivery.getStatus());
        verify(warehouseRepository, times(1)).findById(1L);
        verify(deliveryRepository, times(1)).save(any(Delivery.class));
        verify(deliveryMapper, times(1)).toEntity(deliveryDTO);
        verify(deliveryMapper, times(1)).toDto(delivery);
    }

    @Test
    void getDeliveryById_WhenExists_ShouldReturnDelivery() {
        // Arrange
        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));
        when(deliveryMapper.toDto(delivery)).thenReturn(deliveryDTO);

        // Act
        DeliveryDTO result = deliveryService.getDeliveryById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(deliveryRepository, times(1)).findById(1L);
    }

    @Test
    void getDeliveryById_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(deliveryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
            () -> deliveryService.getDeliveryById(99L));
        verify(deliveryRepository, times(1)).findById(99L);
    }

    @Test
    void getAllDeliveries_ShouldReturnAllDeliveries() {
        // Arrange
        Delivery delivery2 = new Delivery();
        delivery2.setId(2L);
        delivery2.setStatus(DeliveryStatus.PENDING);

        DeliveryDTO deliveryDTO2 = new DeliveryDTO();
        deliveryDTO2.setId(2L);

        List<Delivery> deliveries = Arrays.asList(delivery, delivery2);

        when(deliveryRepository.findAll()).thenReturn(deliveries);
        when(deliveryMapper.toDto(delivery)).thenReturn(deliveryDTO);
        when(deliveryMapper.toDto(delivery2)).thenReturn(deliveryDTO2);

        // Act
        List<DeliveryDTO> result = deliveryService.getAllDeliveries();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(deliveryRepository, times(1)).findAll();
    }

    @Test
    void updateDeliveryStatus_ShouldUpdateStatus() {
        // Arrange
        DeliveryStatus newStatus = DeliveryStatus.DELIVERED;
        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));
        when(deliveryRepository.save(delivery)).thenReturn(delivery);
        when(deliveryMapper.toDto(delivery)).thenReturn(deliveryDTO);

        // Act
        DeliveryDTO result = deliveryService.updateDeliveryStatus(1L, newStatus);

        // Assert
        assertNotNull(result);
        assertEquals(newStatus, delivery.getStatus());
        verify(deliveryRepository, times(1)).findById(1L);
        verify(deliveryRepository, times(1)).save(delivery);
    }

    @Test
    void updateDeliveryStatus_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(deliveryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
            () -> deliveryService.updateDeliveryStatus(99L, DeliveryStatus.DELIVERED));
        verify(deliveryRepository, times(1)).findById(99L);
        verify(deliveryRepository, never()).save(any());
    }

    @Test
    void deleteDelivery_WhenExists_ShouldDeleteSuccessfully() {
        // Arrange
        when(deliveryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(deliveryRepository).deleteById(1L);

        // Act
        deliveryService.deleteDelivery(1L);

        // Assert
        verify(deliveryRepository, times(1)).existsById(1L);
        verify(deliveryRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteDelivery_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(deliveryRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class,
            () -> deliveryService.deleteDelivery(99L));
        verify(deliveryRepository, times(1)).existsById(99L);
        verify(deliveryRepository, never()).deleteById(any());
    }
}

