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


    @Override
    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {

        Warehouse warehouse = warehouseRepository.findById(deliveryDTO.getWarehouseId()).orElseThrow(()-> new RuntimeException("Warehouse not found!"));
        Delivery delivery  = deliveryMapper.toEntity(deliveryDTO);
        delivery.setStatus(DeliveryStatus.PENDING);
        delivery.setDistanceFromWarehouse(calculateDistance(warehouse.getLatitude(), warehouse.getLongitude(), delivery.getLatitude(), delivery.getLongitude()));

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

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of Earth in KM
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
