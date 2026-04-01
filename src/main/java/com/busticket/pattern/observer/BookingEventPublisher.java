package com.busticket.pattern.observer;

import com.busticket.entity.Booking;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * OBSERVER PATTERN: BookingEventPublisher
 * 
 * Acts as the "Subject" in the Observer Pattern.
 * Maintains a list of observers and notifies all of them
 * when a booking event occurs.
 * 
 * SINGLETON PATTERN: Managed as a Spring singleton bean via @Component.
 */
@Component
public class BookingEventPublisher {

    private final List<BookingObserver> observers = new ArrayList<>();

    /**
     * Constructor injection: Spring auto-injects all BookingObserver beans.
     */
    public BookingEventPublisher(List<BookingObserver> observers) {
        this.observers.addAll(observers);
    }

    /**
     * Register a new observer at runtime.
     */
    public void subscribe(BookingObserver observer) {
        observers.add(observer);
    }

    /**
     * Remove an observer.
     */
    public void unsubscribe(BookingObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notify all registered observers about a new booking.
     */
    public void notifyObservers(Booking booking) {
        for (BookingObserver observer : observers) {
            observer.onBookingCreated(booking);
        }
    }
}
