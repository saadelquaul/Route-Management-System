package com.route_management_system.RMS.model.dto;


import com.route_management_system.RMS.model.enums.DeliveryStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class DeliveryDTO {

    private Long id;
    private Double latitude;
    private Double longitude;
    private Double weightKg;
    private Double volumeM3;
    private LocalTime preferredDeliveryStartTime;
    private LocalTime preferredDeliveryEndTime;
    private DeliveryStatus status;
    private double totalDistanceFromWarehouse;

}
