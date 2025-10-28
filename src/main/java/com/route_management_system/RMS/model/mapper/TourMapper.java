package com.route_management_system.RMS.model.mapper;

import com.route_management_system.RMS.model.Tour;
import com.route_management_system.RMS.model.dto.TourDTO;
import lombok.Setter;

import java.util.stream.Collectors;

@Setter
public class TourMapper {

    private DeliveryMapper deliveryMapper;

    public TourMapper(DeliveryMapper deliveryMapper)
    {
        this.deliveryMapper = deliveryMapper;
    }

    public TourDTO toDto(Tour tour) {

        if(tour == null) return null;

        TourDTO dto = new TourDTO();
        dto.setId(tour.getId());
        dto.setStatus(tour.getStatus());
        dto.setAlgorithmUsed(tour.getAlgorithmUsed());
        dto.setTotalDistanceKm(tour.getTotalDistanceKm());
        dto.setDate(tour.getTourDate());

        if(tour.getWarehouse() != null) {
            dto.setWarehouseId(tour.getWarehouse().getId());
        }

        if(tour.getDeliveries() != null) {
            dto.setDeliveries(tour.getDeliveries().stream()
                    .map(deliveryMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;

    }
}
