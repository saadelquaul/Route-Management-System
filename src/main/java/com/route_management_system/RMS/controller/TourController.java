package com.route_management_system.RMS.controller;


import com.route_management_system.RMS.model.dto.TourDTO;
import com.route_management_system.RMS.model.enums.OptimizationAlgorithmType;
import com.route_management_system.RMS.service.TourService;
import lombok.Setter;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tours")
@Setter
public class TourController {

    private TourService tourService;

    @PostMapping("/optimize")
    public ResponseEntity<TourDTO> optimizedTour(@RequestBody TourDTO tourDTO) {

        TourDTO optimizedTour = tourService.getOptimizedTour(tourDTO.getWarehouseId(), tourDTO.getVehicleId());
        return new ResponseEntity<>(optimizedTour, HttpStatus.CREATED);
    }
}
