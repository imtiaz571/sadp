package com.busticket.pattern.factory;

import com.busticket.entity.Bus;

/**
 * FACTORY PATTERN: BusFactory
 * 
 * Creates different types of Bus objects (AC, LUXURY, REGULAR) with
 * pre-configured default settings based on the bus type.
 * 
 * Usage: Instead of creating Bus objects directly in controllers,
 * we delegate creation to this factory which encapsulates the
 * creation logic and sets appropriate defaults.
 */
public class BusFactory {

    /**
     * Creates a Bus with type-specific defaults.
     *
     * @param type      The type of bus: "AC", "LUXURY", or "REGULAR"
     * @param busName   Name of the bus
     * @param busNumber Registration number of the bus
     * @return A fully configured Bus object
     */
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
