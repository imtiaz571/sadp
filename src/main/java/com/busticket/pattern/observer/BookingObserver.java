package com.busticket.pattern.observer;

import com.busticket.entity.Booking;


public interface BookingObserver {


    void onBookingCreated(Booking booking);
}
