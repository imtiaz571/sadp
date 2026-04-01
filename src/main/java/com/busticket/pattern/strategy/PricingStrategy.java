package com.busticket.pattern.strategy;

/**
 * STRATEGY PATTERN: PricingStrategy Interface
 * 
 * Defines a family of pricing algorithms. Each strategy calculates
 * the final fare differently (e.g., regular pricing vs. surge pricing).
 */
public interface PricingStrategy {

    /**
     * Calculate the final fare based on the base fare.
     *
     * @param baseFare The original fare for the schedule
     * @return The calculated fare after applying the strategy
     */
    double calculateFare(double baseFare);

    /**
     * Returns a human-readable name for this pricing strategy.
     */
    String getStrategyName();
}
