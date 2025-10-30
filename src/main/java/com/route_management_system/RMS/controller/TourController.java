package com.route_management_system.RMS.controller;


import com.route_management_system.RMS.model.Tour;
import com.route_management_system.RMS.model.dto.TourDTO;
import com.route_management_system.RMS.service.TourService;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour")
@Setter
public class TourController {

    private TourService tourService;

    @PostMapping
    public ResponseEntity<TourDTO> createTour(@RequestBody TourDTO tourDTO) {

        String algorithm = tourDTO.getAlgorithmUsed().toString();
        if(algorithm == null) algorithm = "";
        TourDTO tour = tourService.getOptimizedTour(tourDTO.getWarehouseId(), tourDTO.getVehicleId(),algorithm);
        return new ResponseEntity<>(tour, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<Tour> getAll(){
            return tourService.getAllTours();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        tourService.deleteTour(id);
        return  ResponseEntity.ok("tour deleted ");
    }

    @GetMapping("total/{id}")
    public  ResponseEntity<?> totalDistance(@PathVariable Long id){
        Double distance = tourService.getTotalDistance(id);
        return ResponseEntity.ok(distance);
    }


}
