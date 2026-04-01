package com.busticket.pattern.strategy;

/**
 * STRATEGY PATTERN: HolidaySurgePricingStrategy
 * 
 * Applies a 50% surcharge on holidays.
 * Simulates real-world pricing during holiday season (e.g., Eid, New Year).
 */
public class HolidaySurgePricingStrategy implements PricingStrategy {

    private static final double SURGE_MULTIPLIER = 1.50; // 50% extra

    @Override
    public double calculateFare(double baseFare) {
        return Math.round(baseFare * SURGE_MULTIPLIER * 100.0) / 100.0;
    }

    @Override
    public String getStrategyName() {
        return "Holiday Surge Pricing (+50%)";
    }
}
