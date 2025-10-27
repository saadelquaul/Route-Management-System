package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.dto.DeliveryDTO;
import com.route_management_system.RMS.model.enums.DeliveryStatus;
import com.route_management_system.RMS.model.mapper.DeliveryMapper;
import com.route_management_system.RMS.repository.DeliveryRepository;
import lombok.Setter;

import java.util.List;

@Setter
public class DeliveryServiceImpl implements DeliveryService{

    private DeliveryRepository deliveryRepository;
    private DeliveryMapper deliveryMapper;



    @Override
    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {
        Delivery delivery  = deliveryMapper.toEntity(deliveryDTO);
        delivery.setStatus(DeliveryStatus.PENDING);

        Delivery savedDelivery = deliveryRepository.save(delivery);
        return deliveryMapper.toDto(savedDelivery);
    }

    @Override
    public DeliveryDTO getDeliveryById(Long deliveryId) {

        return null;
    }

    @Override
    public List<DeliveryDTO> getAllDeliveries() {
        return List.of();
    }

    @Override
    public DeliveryDTO updateDeliveryStaus(Long deliveryId, DeliveryStatus newStatus) {
        return null;
    }

    @Override
    public void deleteDelivery(Long deliveryID) {

    }
}
