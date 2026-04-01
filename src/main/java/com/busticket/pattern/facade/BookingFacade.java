package com.busticket.pattern.facade;

import com.busticket.entity.Booking;
import com.busticket.entity.Schedule;
import com.busticket.entity.User;
import com.busticket.pattern.observer.BookingEventPublisher;
import com.busticket.pattern.strategy.PricingContext;
import com.busticket.repository.BookingRepository;
import com.busticket.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FACADE PATTERN: BookingFacade
 * 
 * Provides a simplified interface for the complex booking workflow.
 * Behind the scenes, it orchestrates:
 *   1. Seat availability check
 *   2. Dynamic pricing calculation (Strategy Pattern)
 *   3. Booking creation and persistence
 *   4. Seat count update
 *   5. Notification dispatch (Observer Pattern)
 * 
 * The controller only needs to call ONE method: bookTicket()
 */
@Service
public class BookingFacade {

    private final ScheduleRepository scheduleRepository;
    private final BookingRepository bookingRepository;
    private final PricingContext pricingContext;
    private final BookingEventPublisher eventPublisher;

    public BookingFacade(ScheduleRepository scheduleRepository,
                         BookingRepository bookingRepository,
                         PricingContext pricingContext,
                         BookingEventPublisher eventPublisher) {
        this.scheduleRepository = scheduleRepository;
        this.bookingRepository = bookingRepository;
        this.pricingContext = pricingContext;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Complete booking workflow in ONE call.
     *
     * @param user          The logged-in user
     * @param scheduleId    The selected schedule
     * @param seatNumber    The selected seat number
     * @param passengerName Name of the passenger
     * @return The confirmed Booking object
     * @throws RuntimeException if seat is unavailable or schedule not found
     */
    @Transactional
    public Booking bookTicket(User user, Long scheduleId, int seatNumber, String passengerName) {

        // Step 1: Fetch the schedule
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + scheduleId));

        // Step 2: Check seat availability
        if (schedule.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available on this schedule.");
        }

        // Check if this specific seat is already booked
        if (bookingRepository.existsByScheduleIdAndSeatNumber(scheduleId, seatNumber)) {
            throw new RuntimeException("Seat " + seatNumber + " is already booked.");
        }

        // Step 3: Calculate fare using Strategy Pattern
        double baseFare = schedule.getFare();
        double finalFare = pricingContext.calculateFare(
                baseFare,
                schedule.getDepartureTime().toLocalDate()
        );

        // Step 4: Create and save the booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSchedule(schedule);
        booking.setSeatNumber(seatNumber);
        booking.setPassengerName(passengerName);
        booking.setTotalFare(finalFare);
        booking.setBookingStatus("CONFIRMED");
        booking.setPaymentStatus("PAID");

        Booking savedBooking = bookingRepository.save(booking);

        // Step 5: Update available seats
        schedule.setAvailableSeats(schedule.getAvailableSeats() - 1);
        scheduleRepository.save(schedule);

        // Step 6: Notify observers (Observer Pattern)
        eventPublisher.notifyObservers(savedBooking);

        return savedBooking;
    }

    /**
     * Get the pricing strategy name for a given schedule.
     */
    public String getPricingInfo(Schedule schedule) {
        return pricingContext
                .getStrategy(schedule.getDepartureTime().toLocalDate())
                .getStrategyName();
    }

    /**
     * Calculate the dynamic fare for a schedule.
     */
    public double getCalculatedFare(Schedule schedule) {
        return pricingContext.calculateFare(
                schedule.getFare(),
                schedule.getDepartureTime().toLocalDate()
        );
    }
}
