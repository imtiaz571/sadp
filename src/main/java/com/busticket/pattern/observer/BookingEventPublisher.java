package com.busticket.pattern.observer;

import com.busticket.entity.Booking;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class BookingEventPublisher {

    private final List<BookingObserver> observers = new ArrayList<>();

   
    public BookingEventPublisher(List<BookingObserver> observers) {
        this.observers.addAll(observers);
    }

    public void subscribe(BookingObserver observer) {
        observers.add(observer);
    }

    
    public void unsubscribe(BookingObserver observer) {
        observers.remove(observer);
    }

    
    public void notifyObservers(Booking booking) {
        for (BookingObserver observer : observers) {
            observer.onBookingCreated(booking);
        }
    }
}
