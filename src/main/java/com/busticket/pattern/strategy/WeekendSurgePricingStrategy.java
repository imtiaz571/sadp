package com.busticket.pattern.strategy;

/**
 * STRATEGY PATTERN: WeekendSurgePricingStrategy
 * 
 * Applies a 25% surcharge on weekends (Saturday & Sunday).
 * Simulates real-world surge pricing during high-demand periods.
 */
public class WeekendSurgePricingStrategy implements PricingStrategy {

    private static final double SURGE_MULTIPLIER = 1.25; // 25% extra

    @Override
    public double calculateFare(double baseFare) {
        return Math.round(baseFare * SURGE_MULTIPLIER * 100.0) / 100.0;
    }

    @Override
    public String getStrategyName() {
        return "Weekend Surge Pricing (+25%)";
    }
}
