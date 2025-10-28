package com.route_management_system.RMS.controller;


import com.route_management_system.RMS.model.dto.VehicleDTO;
import com.route_management_system.RMS.model.mapper.VehicleMapper;
import com.route_management_system.RMS.service.VehicleService;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicles")
@Setter
public class VehicleController {

    VehicleMapper vehicleMapper;
    VehicleService vehicleService;


    @PostMapping
    public ResponseEntity<VehicleDTO> createVehicle(@RequestBody VehicleDTO vehicleDTO){

        VehicleDTO createdVehicle = vehicleService.createVehicle(vehicleDTO);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

}
