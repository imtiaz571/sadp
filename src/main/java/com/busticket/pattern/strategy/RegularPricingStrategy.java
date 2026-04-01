package com.busticket.pattern.strategy;

/**
 * STRATEGY PATTERN: RegularPricingStrategy
 * 
 * No surcharge — the fare remains as-is (1x multiplier).
 * Applied on normal weekdays.
 */
public class RegularPricingStrategy implements PricingStrategy {

    @Override
    public double calculateFare(double baseFare) {
        return baseFare; // No surcharge
    }

    @Override
    public String getStrategyName() {
        return "Regular Pricing";
    }
}
