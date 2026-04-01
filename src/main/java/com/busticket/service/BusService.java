package com.busticket.service;

import com.busticket.entity.Bus;
import com.busticket.pattern.factory.BusFactory;
import com.busticket.repository.BusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BusService - Manages bus CRUD operations.
 * Uses the FACTORY PATTERN via BusFactory for creating buses.
 */
@Service
public class BusService {

    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    /**
     * Create a new bus using the Factory Pattern.
     */
    public Bus createBus(String type, String busName, String busNumber) {
        Bus bus = BusFactory.createBus(type, busName, busNumber);
        return busRepository.save(bus);
    }

    /**
     * Create a bus with custom seat count (overrides factory defaults).
     */
    public Bus createBusCustom(String type, String busName, String busNumber, int totalSeats, String amenities) {
        Bus bus = BusFactory.createBus(type, busName, busNumber);
        bus.setTotalSeats(totalSeats);
        if (amenities != null && !amenities.isBlank()) {
            bus.setAmenities(amenities);
        }
        return busRepository.save(bus);
    }

    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    public Bus findById(Long id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + id));
    }

    public Bus updateBus(Long id, String busName, String busNumber, String busType,
                          int totalSeats, String amenities) {
        Bus bus = findById(id);
        bus.setBusName(busName);
        bus.setBusNumber(busNumber);
        bus.setBusType(busType);
        bus.setTotalSeats(totalSeats);
        bus.setAmenities(amenities);
        return busRepository.save(bus);
    }

    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }
}
