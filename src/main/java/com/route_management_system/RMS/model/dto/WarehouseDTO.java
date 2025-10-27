package com.route_management_system.RMS.model.dto;


import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalTime;


@Data
@NoArgsConstructor
public class WarehouseDTO {

    private Long id;
    private Double longitude;
    private Double latitude;
    private LocalTime openingTime;
    private LocalTime closingTime;

}
