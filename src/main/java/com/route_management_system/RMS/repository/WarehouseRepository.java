package com.route_management_system.RMS.repository;

import com.route_management_system.RMS.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findWarehouseByLongitudeAndLatitude(double longitude, double latitude);
}
