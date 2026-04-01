package com.busticket.pattern.observer;

import com.busticket.entity.Booking;

/**
 * OBSERVER PATTERN: BookingObserver Interface
 * 
 * Any class that wants to be notified when a booking is made
 * must implement this interface.
 */
public interface BookingObserver {

    /**
     * Called when a new booking is successfully created.
     *
     * @param booking The newly created booking
     */
    void onBookingCreated(Booking booking);
}
