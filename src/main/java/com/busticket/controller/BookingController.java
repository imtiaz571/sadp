package com.busticket.controller;

import com.busticket.entity.Booking;
import com.busticket.entity.Notification;
import com.busticket.entity.Schedule;
import com.busticket.entity.User;
import com.busticket.pattern.facade.BookingFacade;
import com.busticket.pattern.iterator.SeatCollection;
import com.busticket.repository.UserRepository;
import com.busticket.service.BookingService;
import com.busticket.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * BookingController - Handles seat selection, booking, ticket view, and history.
 * Uses the FACADE PATTERN (BookingFacade) for the booking workflow.
 * Uses the ITERATOR PATTERN (SeatCollection) for seat display.
 *
 * UPDATED: Allows guest users to book without login.
 * A guest user record is created on-the-fly for database integrity.
 */
@Controller
public class BookingController {

    private final BookingFacade bookingFacade;
    private final BookingService bookingService;
    private final ScheduleService scheduleService;
    private final UserRepository userRepository;

    public BookingController(BookingFacade bookingFacade,
                              BookingService bookingService,
                              ScheduleService scheduleService,
                              UserRepository userRepository) {
        this.bookingFacade = bookingFacade;
        this.bookingService = bookingService;
        this.scheduleService = scheduleService;
        this.userRepository = userRepository;
    }

    // ==================== BOOKING PAGE (Seat Selection) ====================

    @GetMapping("/book/{scheduleId}")
    public String showBookingPage(@PathVariable Long scheduleId,
                                   HttpSession session,
                                   Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        Schedule schedule = scheduleService.findById(scheduleId);

        // ITERATOR PATTERN: Get available seats as a SeatCollection
        SeatCollection availableSeats = scheduleService.getAvailableSeats(scheduleId);

        // Dynamic fare
        double dynamicFare = bookingFacade.getCalculatedFare(schedule);
        String pricingName = bookingFacade.getPricingInfo(schedule);

        model.addAttribute("schedule", schedule);
        model.addAttribute("availableSeats", availableSeats.getSeats());
        model.addAttribute("dynamicFare", dynamicFare);
        model.addAttribute("pricingName", pricingName);
        model.addAttribute("user", user); // may be null for guests
        model.addAttribute("isGuest", user == null);

        return "booking";
    }

    // ==================== PROCESS BOOKING ====================

    @PostMapping("/book")
    public String processBooking(@RequestParam Long scheduleId,
                                  @RequestParam int seatNumber,
                                  @RequestParam String passengerName,
                                  @RequestParam(required = false) String guestPhone,
                                  @RequestParam(required = false) String guestEmail,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");

        // If no logged-in user, create or find a guest user
        if (user == null) {
            user = getOrCreateGuestUser(passengerName, guestPhone, guestEmail);
        }

        try {
            // FACADE PATTERN: One call handles the entire workflow
            Booking booking = bookingFacade.bookTicket(user, scheduleId, seatNumber, passengerName);
            return "redirect:/ticket/" + booking.getId();
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/book/" + scheduleId;
        }
    }

    // ==================== TICKET VIEW (No login required) ====================

    @GetMapping("/ticket/{bookingId}")
    public String showTicket(@PathVariable Long bookingId, Model model) {
        Booking booking = bookingService.findById(bookingId);
        model.addAttribute("booking", booking);
        return "ticket";
    }

    // ==================== BOOKING HISTORY (Login required) ====================

    @GetMapping("/my-bookings")
    public String showBookingHistory(HttpSession session, Model model,
                                      RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login to view your bookings.");
            return "redirect:/login";
        }

        List<Booking> bookings = bookingService.findByUserId(user.getId());
        model.addAttribute("bookings", bookings);
        model.addAttribute("user", user);
        return "booking-history";
    }

    // ==================== NOTIFICATIONS (Login required) ====================

    @GetMapping("/notifications")
    public String showNotifications(HttpSession session, Model model,
                                     RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please login.");
            return "redirect:/login";
        }

        List<Notification> notifications = bookingService.getUserNotifications(user.getId());
        bookingService.markNotificationsAsRead(user.getId());
        model.addAttribute("notifications", notifications);
        model.addAttribute("user", user);
        return "notifications";
    }

    // ==================== HELPER: Guest User ====================

    /**
     * Creates or retrieves a guest user for non-logged-in bookings.
     * If the guest provides an email that already exists, reuse that user.
     * Otherwise, create a new guest user record.
     */
    private User getOrCreateGuestUser(String name, String phone, String email) {
        // If guest provided an email, try to find existing user
        if (email != null && !email.isBlank()) {
            Optional<User> existing = userRepository.findByEmail(email);
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        // Create a new guest user
        String guestEmail = (email != null && !email.isBlank())
                ? email
                : "guest_" + System.currentTimeMillis() + "@busgo.com";
        String guestPhone = (phone != null && !phone.isBlank()) ? phone : "N/A";

        User guest = new User(
                name,
                guestEmail,
                "guest_no_login",  // placeholder password (guest can't login)
                guestPhone,
                "GUEST"
        );
        return userRepository.save(guest);
    }
}
