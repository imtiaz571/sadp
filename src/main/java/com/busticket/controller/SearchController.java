package com.busticket.controller;

import com.busticket.entity.Schedule;
import com.busticket.pattern.facade.BookingFacade;
import com.busticket.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

/**
 * SearchController - Handles bus search and results.
 */
@Controller
public class SearchController {

    private final ScheduleService scheduleService;
    private final BookingFacade bookingFacade;

    public SearchController(ScheduleService scheduleService, BookingFacade bookingFacade) {
        this.scheduleService = scheduleService;
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/search")
    public String showSearchPage() {
        return "search";
    }

    @GetMapping("/results")
    public String showResults(@RequestParam String origin,
                               @RequestParam String destination,
                               @RequestParam String travelDate,
                               Model model) {
        LocalDate date = LocalDate.parse(travelDate);
        List<Schedule> schedules = scheduleService.searchSchedules(origin, destination, date);

        // Add pricing info for each schedule
        model.addAttribute("schedules", schedules);
        model.addAttribute("origin", origin);
        model.addAttribute("destination", destination);
        model.addAttribute("travelDate", travelDate);

        // Calculate dynamic fares
        java.util.Map<Long, Double> dynamicFares = new java.util.HashMap<>();
        java.util.Map<Long, String> pricingNames = new java.util.HashMap<>();
        for (Schedule s : schedules) {
            dynamicFares.put(s.getId(), bookingFacade.getCalculatedFare(s));
            pricingNames.put(s.getId(), bookingFacade.getPricingInfo(s));
        }
        model.addAttribute("dynamicFares", dynamicFares);
        model.addAttribute("pricingNames", pricingNames);

        return "results";
    }
}
