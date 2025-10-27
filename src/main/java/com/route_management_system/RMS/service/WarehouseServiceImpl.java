package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Warehouse;
import com.route_management_system.RMS.model.dto.WarehouseDTO;
import com.route_management_system.RMS.model.mapper.WarehouseMapper;
import com.route_management_system.RMS.repository.WarehouseRepository;
import lombok.Setter;

import java.awt.image.renderable.RenderableImage;

@Setter
public class WarehouseServiceImpl implements WarehouseService{

    WarehouseMapper warehouseMapper;
    WarehouseRepository warehouseRepository;

    @Override
    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseMapper.toEntity(warehouseDTO);
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        return warehouseMapper.toDto(savedWarehouse);
    }

    @Override
    public WarehouseDTO getWarehouseById(Long warehouseId) {
        Warehouse warehouse =  warehouseRepository.findById(warehouseId).orElseThrow(()-> new RuntimeException("Warehouse not found!"));
        return warehouseMapper.toDto(warehouse);
    }
}
