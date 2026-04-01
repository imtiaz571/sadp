package com.busticket.controller;

import com.busticket.entity.User;
import com.busticket.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

/**
 * HomeController - Landing page and user home.
 */
@Controller
public class HomeController {

    private final BookingService bookingService;

    public HomeController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String homePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            model.addAttribute("user", user);
            long unreadCount = bookingService.getUnreadNotificationCount(user.getId());
            model.addAttribute("unreadCount", unreadCount);
        }
        return "index";
    }
}
