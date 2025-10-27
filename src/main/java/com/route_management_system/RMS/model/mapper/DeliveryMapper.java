package com.route_management_system.RMS.model.mapper;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.dto.DeliveryDTO;

public class DeliveryMapper {


    public DeliveryDTO toDto(Delivery delivery) {
        if (delivery == null) return null;

        DeliveryDTO dto = new DeliveryDTO();
        dto.setId(delivery.getId());
        dto.setLongitude(delivery.getLongitude());
        dto.setLatitude(delivery.getLatitude());
        dto.setStatus(delivery.getStatus());
        dto.setPreferredDeliveryEndTime(delivery.getPreferredDeliveryTimeEnd());
        dto.setPreferredDeliveryStartTime(delivery.getPreferredDeliveryTimeStart());
        dto.setWeightKg(delivery.getWeightKg());
        dto.setVolumeM3(delivery.getVolumeM3());
        dto.setTotalDistanceFromWarehouse(delivery.getDistanceFromWarehouse());
        return dto;
    }

    public Delivery toEntity(DeliveryDTO dto) {
        if(dto == null) return null;

        Delivery delivery = new Delivery();
        delivery.setLatitude(dto.getLatitude());
        delivery.setLongitude(dto.getLongitude());
        delivery.setStatus(dto.getStatus());
        delivery.setPreferredDeliveryTimeEnd(dto.getPreferredDeliveryEndTime());
        delivery.setVolumeM3(dto.getVolumeM3());
        delivery.setWeightKg(dto.getWeightKg());
        delivery.setPreferredDeliveryTimeStart(dto.getPreferredDeliveryStartTime());
        delivery.setDistanceFromWarehouse(dto.getTotalDistanceFromWarehouse());

        return delivery;

    }
}
