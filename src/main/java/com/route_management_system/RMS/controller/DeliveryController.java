package com.route_management_system.RMS.controller;



import com.route_management_system.RMS.model.dto.DeliveryDTO;
import com.route_management_system.RMS.model.enums.DeliveryStatus;
import com.route_management_system.RMS.service.DeliveryService;
import lombok.Setter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/delivery")
@Setter
public class DeliveryController {

    DeliveryService deliveryService;


    @PostMapping
    public ResponseEntity<DeliveryDTO> createDelivery(DeliveryDTO deliveryDTO) {
        DeliveryDTO createdDelivery = deliveryService.createDelivery(deliveryDTO);
        return new ResponseEntity<>(createdDelivery, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> getDeliveryById(Long id) {
        DeliveryDTO delivery = deliveryService.getDeliveryById(id);
        return ResponseEntity.ok(delivery);
    }

    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries() {
        List<DeliveryDTO> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DeliveryDTO> updateDeliveryStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {

        DeliveryStatus newStatus = DeliveryStatus.valueOf(statusUpdate.get("status"));

        DeliveryDTO updatedDelivery = deliveryService.updateDeliveryStatus(id, newStatus);
        return ResponseEntity.ok(updatedDelivery);
    }
}
