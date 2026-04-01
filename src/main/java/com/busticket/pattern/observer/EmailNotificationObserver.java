package com.busticket.pattern.observer;

import com.busticket.entity.Booking;
import com.busticket.entity.Notification;
import com.busticket.repository.NotificationRepository;
import org.springframework.stereotype.Component;

/**
 * OBSERVER PATTERN: EmailNotificationObserver
 * 
 * Simulates sending an email notification when a booking is created.
 * In production, this would integrate with an email service (e.g., JavaMailSender).
 * For this demo, it saves a notification record to the database.
 */
@Component
public class EmailNotificationObserver implements BookingObserver {

    private final NotificationRepository notificationRepository;

    public EmailNotificationObserver(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void onBookingCreated(Booking booking) {
        String message = String.format(
            "📧 Email: Your booking #%d is confirmed! Bus: %s, Seat: %d, Route: %s → %s, Fare: ৳%.2f",
            booking.getId(),
            booking.getSchedule().getBus().getBusName(),
            booking.getSeatNumber(),
            booking.getSchedule().getRoute().getOrigin(),
            booking.getSchedule().getRoute().getDestination(),
            booking.getTotalFare()
        );

        Notification notification = new Notification(booking.getUser(), message);
        notificationRepository.save(notification);

        // In production: send actual email here
        System.out.println("[Observer] " + message);
    }
}
