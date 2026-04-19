package com.busticket.pattern.factory;

import com.busticket.entity.Bus;


public class BusFactory {

   
    public static Bus createBus(String type, String busName, String busNumber) {
        return switch (type.toUpperCase()) {
            case "AC" -> createAcBus(busName, busNumber);
            case "LUXURY" -> createLuxuryBus(busName, busNumber);
            case "REGULAR" -> createRegularBus(busName, busNumber);
            default -> throw new IllegalArgumentException("Unknown bus type: " + type);
        };
    }

    private static Bus createAcBus(String busName, String busNumber) {
        Bus bus = new Bus();
        bus.setBusName(busName);
        bus.setBusNumber(busNumber);
        bus.setBusType("AC");
        bus.setTotalSeats(36);
        bus.setAmenities("Air Conditioning, Reclining Seats, Charging Ports, Reading Lights");
        return bus;
    }

    private static Bus createLuxuryBus(String busName, String busNumber) {
        Bus bus = new Bus();
        bus.setBusName(busName);
        bus.setBusNumber(busNumber);
        bus.setBusType("LUXURY");
        bus.setTotalSeats(24);
        bus.setAmenities("AC, WiFi, Entertainment System, Snacks, Blankets, Extra Legroom");
        return bus;
    }

    private static Bus createRegularBus(String busName, String busNumber) {
        Bus bus = new Bus();
        bus.setBusName(busName);
        bus.setBusNumber(busNumber);
        bus.setBusType("REGULAR");
        bus.setTotalSeats(48);
        bus.setAmenities("Standard Seating, Fan");
        return bus;
    }
}
