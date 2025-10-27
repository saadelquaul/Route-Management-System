package com.route_management_system.RMS.controller;


import com.route_management_system.RMS.service.DeliveryService;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delivery")
@Setter
public class DeliveryController {

    DeliveryService deliveryService;


}
