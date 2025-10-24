package com.route_management_system.RMS.repository;

import com.route_management_system.RMS.model.Delivery;
import com.route_management_system.RMS.model.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByStatus(DeliveryStatus status);
}
