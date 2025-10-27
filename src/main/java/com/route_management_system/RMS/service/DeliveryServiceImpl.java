package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.dto.DeliveryDTO;
import com.route_management_system.RMS.model.enums.DeliveryStatus;
import com.route_management_system.RMS.model.mapper.DeliveryMapper;
import com.route_management_system.RMS.repository.DeliveryRepository;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

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
