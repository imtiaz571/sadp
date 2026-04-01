package com.busticket.service;

import com.busticket.entity.Booking;
import com.busticket.entity.Notification;
import com.busticket.repository.BookingRepository;
import com.busticket.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BookingService - Additional booking queries (the main booking logic
 * lives in BookingFacade following the Facade Pattern).
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final NotificationRepository notificationRepository;

    public BookingService(BookingRepository bookingRepository,
                          NotificationRepository notificationRepository) {
        this.bookingRepository = bookingRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<Booking> findByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public long getUnreadNotificationCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    public void markNotificationsAsRead(Long userId) {
        List<Notification> unread = notificationRepository.findByUserIdAndIsReadFalse(userId);
        for (Notification n : unread) {
            n.setRead(true);
            notificationRepository.save(n);
        }
    }
}
