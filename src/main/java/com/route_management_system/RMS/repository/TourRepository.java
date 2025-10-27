package com.route_management_system.RMS.repository;

import com.route_management_system.RMS.model.Tour;
import com.route_management_system.RMS.model.enums.TourStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {


    List<Tour> findByStatus(TourStatus status);
    List<Tour> findByTOurDate(LocalDate date);
}
