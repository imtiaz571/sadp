package com.busticket.pattern.observer;

import com.busticket.entity.Booking;
import com.busticket.entity.Notification;
import com.busticket.repository.NotificationRepository;
import org.springframework.stereotype.Component;

/**
 * OBSERVER PATTERN: SMSNotificationObserver
 * 
 * Simulates sending an SMS notification when a booking is created.
 * In production, this would integrate with an SMS gateway (e.g., Twilio).
 */
@Component
public class SMSNotificationObserver implements BookingObserver {

    private final NotificationRepository notificationRepository;

    public SMSNotificationObserver(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void onBookingCreated(Booking booking) {
        String message = String.format(
            "📱 SMS: Booking #%d confirmed! Seat %d on %s. Departure: %s. Fare: ৳%.2f",
            booking.getId(),
            booking.getSeatNumber(),
            booking.getSchedule().getBus().getBusName(),
            booking.getSchedule().getDepartureTime().toString(),
            booking.getTotalFare()
        );

        Notification notification = new Notification(booking.getUser(), message);
        notificationRepository.save(notification);

        // In production: send actual SMS here
        System.out.println("[Observer] " + message);
    }
}
