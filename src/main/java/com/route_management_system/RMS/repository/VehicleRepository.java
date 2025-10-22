package com.route_management_system.RMS.repository;

import com.route_management_system.RMS.model.Vehicle;
import com.route_management_system.RMS.model.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    public List<Vehicle> findByType(VehicleType type);

}
