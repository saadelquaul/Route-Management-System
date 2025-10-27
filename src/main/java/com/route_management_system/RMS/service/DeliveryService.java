package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.dto.DeliveryDTO;
import com.route_management_system.RMS.model.enums.DeliveryStatus;

import java.util.List;

public interface DeliveryService {

    DeliveryDTO createDelivery(DeliveryDTO deliveryDTO);
    DeliveryDTO getDeliveryById(Long deliveryId);

    List<DeliveryDTO> getAllDeliveries();
    DeliveryDTO updateDeliveryStaus(Long deliveryId, DeliveryStatus newStatus);
    void deleteDelivery(Long deliveryID);

    }

