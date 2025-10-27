package com.route_management_system.RMS.model.dto;



import com.route_management_system.RMS.model.enums.OptimizationAlgorithmType;
import com.route_management_system.RMS.model.enums.TourStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TourDTO {

    private Long id ;
    private Long vehicleId;
    private Long warehouseId;
    private TourStatus status;
    private OptimizationAlgorithmType algorithmUsed;
    private Double totalDistanceKm;

    private List<DeliveryDTO> deliveries;
}
