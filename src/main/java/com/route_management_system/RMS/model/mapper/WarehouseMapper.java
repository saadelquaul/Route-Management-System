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
        dto.setClosingTime(warehouse.getClosingDate());
        dto.setOpeningTime(warehouse.getOpeningDate());

        return dto;
    }


    public Warehouse toEntity(WarehouseDTO dto) {
        if (dto == null) return null;

        Warehouse warehouse = new Warehouse();
        warehouse.setLatitude(dto.getLatitude());
        warehouse.setLongitude(dto.getLongitude());
        warehouse.setClosingDate(dto.getClosingTime());
        warehouse.setOpeningDate(dto.getOpeningTime());

        return warehouse;
    }
}
