package com.busticket.repository;

import com.busticket.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findByBusType(String busType);
    boolean existsByBusNumber(String busNumber);
}
