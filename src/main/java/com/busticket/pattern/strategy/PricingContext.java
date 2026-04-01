package com.busticket.pattern.strategy;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Set;

/**
 * STRATEGY PATTERN: PricingContext
 * 
 * Determines which PricingStrategy to apply based on the travel date.
 * This is the "Context" in the Strategy Pattern — it holds a reference
 * to the current strategy and delegates fare calculation to it.
 * 
 * SINGLETON PATTERN: Managed as a Spring singleton bean via @Component.
 */
@Component
public class PricingContext {

    // Define some holidays (month-day format for simplicity)
    private static final Set<MonthDay> HOLIDAYS = Set.of(
        MonthDay.of(1, 1),   // New Year
        MonthDay.of(2, 21),  // Language Day
        MonthDay.of(3, 26),  // Independence Day
        MonthDay.of(5, 1),   // May Day
        MonthDay.of(12, 16), // Victory Day
        MonthDay.of(12, 25)  // Christmas
    );

    /**
     * Automatically selects the correct pricing strategy based on the date.
     */
    public PricingStrategy getStrategy(LocalDate travelDate) {
        MonthDay monthDay = MonthDay.from(travelDate);

        if (HOLIDAYS.contains(monthDay)) {
            return new HolidaySurgePricingStrategy();
        }

        DayOfWeek day = travelDate.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return new WeekendSurgePricingStrategy();
        }

        return new RegularPricingStrategy();
    }

    /**
     * Calculate fare using the appropriate strategy for the given date.
     */
    public double calculateFare(double baseFare, LocalDate travelDate) {
        PricingStrategy strategy = getStrategy(travelDate);
        return strategy.calculateFare(baseFare);
    }
}
