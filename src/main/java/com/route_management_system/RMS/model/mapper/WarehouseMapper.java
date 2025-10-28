package com.route_management_system.RMS.model.mapper;

import com.route_management_system.RMS.model.Warehouse;
import com.route_management_system.RMS.model.dto.WarehouseDTO;

public class WarehouseMapper {



    public WarehouseDTO toDto(Warehouse warehouse) {
        if (warehouse == null) {
            return null;
        }

        WarehouseDTO dto = new WarehouseDTO();
        dto.setId(warehouse.getId());
        dto.setLatitude(warehouse.getLatitude());
        dto.setLongitude(warehouse.getLongitude());
        dto.setClosingTime(warehouse.getClosingTime());
        dto.setOpeningTime(warehouse.getOpeningTime());

        return dto;
    }


    public Warehouse toEntity(WarehouseDTO dto) {
        if (dto == null) return null;

        Warehouse warehouse = new Warehouse();
        warehouse.setLatitude(dto.getLatitude());
        warehouse.setLongitude(dto.getLongitude());
        warehouse.setClosingTime(dto.getClosingTime());
        warehouse.setOpeningTime(dto.getOpeningTime());

        return warehouse;
    }
}
