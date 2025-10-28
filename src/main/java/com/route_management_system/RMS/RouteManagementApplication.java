package com.route_management_system.RMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class RouteManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouteManagementApplication.class, args);
	}

}
