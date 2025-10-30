package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.Warehouse;
import com.route_management_system.RMS.model.dto.DeliveryDTO;
import com.route_management_system.RMS.model.enums.DeliveryStatus;
import com.route_management_system.RMS.model.mapper.DeliveryMapper;
import com.route_management_system.RMS.repository.DeliveryRepository;
import com.route_management_system.RMS.repository.WarehouseRepository;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
public class DeliveryServiceImpl implements DeliveryService{

    private WarehouseRepository warehouseRepository;
    private DeliveryRepository deliveryRepository;
    private DeliveryMapper deliveryMapper;
    private TourService tourService;



    @Override
    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {

        Warehouse warehouse = warehouseRepository.findById(deliveryDTO.getWarehouseId()).orElseThrow(()-> new RuntimeException("Warehouse not found!"));
        Delivery delivery  = deliveryMapper.toEntity(deliveryDTO);
        delivery.setStatus(DeliveryStatus.PENDING);
        delivery.setDistanceFromWarehouse(tourService.calculateDistance(warehouse.getLatitude(), warehouse.getLongitude(), delivery.getLatitude(), delivery.getLongitude()));

        Delivery savedDelivery = deliveryRepository.save(delivery);
        return deliveryMapper.toDto(savedDelivery);
    }

    @Override
    public DeliveryDTO getDeliveryById(Long deliveryId) {

        Delivery delivery  = deliveryRepository.findById(deliveryId).orElseThrow(() -> new RuntimeException("Delivery found!"));

        return deliveryMapper.toDto(delivery);
    }

    @Override
    public List<DeliveryDTO> getAllDeliveries() {
        return deliveryRepository.findAll().stream().map(deliveryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public DeliveryDTO updateDeliveryStatus(Long deliveryId, DeliveryStatus newStatus) {

        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new RuntimeException("Delivery not found"));
        delivery.setStatus(newStatus);
        Delivery updatedDelivery= deliveryRepository.save(delivery);
        return deliveryMapper.toDto(updatedDelivery);
    }

    @Override
    public void deleteDelivery(Long deliveryID) {
        if(!deliveryRepository.existsById(deliveryID)) {
            throw  new RuntimeException("Delivery not found!");
        }

        deliveryRepository.deleteById(deliveryID);

    }
}
