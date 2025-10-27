package com.route_management_system.RMS.controller;


import com.route_management_system.RMS.model.dto.WarehouseDTO;
import com.route_management_system.RMS.service.WarehouseService;
import lombok.Setter;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse")
@Setter
public class WarehouseController {

    private WarehouseService warehouseService;


    @PostMapping
    public ResponseEntity<WarehouseDTO> createWarehouse(@RequestBody WarehouseDTO warehouseDTO){
        WarehouseDTO createdWarehouse = warehouseService.createWarehouse(warehouseDTO);
        return new ResponseEntity<>(createdWarehouse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDTO> getWarehouseById(@PathVariable Long id) {
        WarehouseDTO warehouseDTO = warehouseService.getWarehouseById(id);
        return ResponseEntity.ok(warehouseDTO);
    }

}
