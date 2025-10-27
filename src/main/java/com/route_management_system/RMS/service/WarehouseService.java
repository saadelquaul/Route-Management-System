package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.dto.WarehouseDTO;

public interface WarehouseService {

    WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO);
    WarehouseDTO getWarehouseById(Long warehouseId);

}
