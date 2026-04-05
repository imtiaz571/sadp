package com.busticket.controller;

import com.busticket.entity.*;
import com.busticket.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AdminController - Full CRUD for Buses, Routes, Schedules, and admin dashboard.
 * Uses the FACTORY PATTERN (via BusService) for bus creation.
 * Uses the MEMENTO PATTERN (via ScheduleService) for schedule revert.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BusService busService;
    private final RouteService routeService;
    private final ScheduleService scheduleService;
    private final BookingService bookingService;

    public AdminController(BusService busService,
                            RouteService routeService,
                            ScheduleService scheduleService,
                            BookingService bookingService) {
        this.busService = busService;
        this.routeService = routeService;
        this.scheduleService = scheduleService;
        this.bookingService = bookingService;
    }

    // ===================== ADMIN CHECK =====================

    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        return "ADMIN".equals(role);
    }

    // ===================== DASHBOARD =====================

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isAdmin(session)) {
            ra.addFlashAttribute("error", "Admin access only.");
            return "redirect:/login";
        }

        model.addAttribute("buses", busService.findAll());
        model.addAttribute("routes", routeService.findAll());
        model.addAttribute("schedules", scheduleService.findAll());
        model.addAttribute("bookings", bookingService.findAll());
        model.addAttribute("user", session.getAttribute("loggedInUser"));
        return "admin-dashboard";
    }

    // ===================== BUS CRUD =====================

    @GetMapping("/buses")
    public String listBuses(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        model.addAttribute("buses", busService.findAll());
        return "admin-buses";
    }

    @GetMapping("/buses/add")
    public String showAddBusForm(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        return "admin-bus-form";
    }

    @PostMapping("/buses/add")
    public String addBus(@RequestParam String busName,
                          @RequestParam String busNumber,
                          @RequestParam String busType,
                          @RequestParam int totalSeats,
                          @RequestParam(required = false) String amenities,
                          HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }

        try {
            // FACTORY PATTERN: Bus creation via BusService -> BusFactory
            busService.createBusCustom(busType, busName, busNumber, totalSeats, amenities);
            ra.addFlashAttribute("success", "Bus added successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/buses";
    }

    @GetMapping("/buses/edit/{id}")
    public String showEditBusForm(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        model.addAttribute("bus", busService.findById(id));
        return "admin-bus-form";
    }

    @PostMapping("/buses/edit/{id}")
    public String editBus(@PathVariable Long id,
                           @RequestParam String busName,
                           @RequestParam String busNumber,
                           @RequestParam String busType,
                           @RequestParam int totalSeats,
                           @RequestParam(required = false) String amenities,
                           HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }

        try {
            busService.updateBus(id, busName, busNumber, busType, totalSeats, amenities);
            ra.addFlashAttribute("success", "Bus updated successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/buses";
    }

    @GetMapping("/buses/delete/{id}")
    public String deleteBus(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        busService.deleteBus(id);
        ra.addFlashAttribute("success", "Bus deleted.");
        return "redirect:/admin/buses";
    }

    // ===================== ROUTE CRUD =====================

    @GetMapping("/routes")
    public String listRoutes(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        model.addAttribute("routes", routeService.findAll());
        return "admin-routes";
    }

    @GetMapping("/routes/add")
    public String showAddRouteForm(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        model.addAttribute("parentRoutes", routeService.findTopLevelRoutes());
        return "admin-route-form";
    }

    @PostMapping("/routes/add")
    public String addRoute(@RequestParam String routeName,
                            @RequestParam String origin,
                            @RequestParam String destination,
                            @RequestParam(required = false) Double distanceKm,
                            @RequestParam(required = false) Long parentRouteId,
                            HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }

        try {
            if (parentRouteId != null) {
                routeService.addSubRoute(parentRouteId, routeName, origin, destination, distanceKm);
            } else {
                routeService.createRoute(routeName, origin, destination, distanceKm);
            }
            ra.addFlashAttribute("success", "Route added successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/routes";
    }

    @GetMapping("/routes/edit/{id}")
    public String showEditRouteForm(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        model.addAttribute("route", routeService.findById(id));
        model.addAttribute("parentRoutes", routeService.findTopLevelRoutes());
        return "admin-route-form";
    }

    @PostMapping("/routes/edit/{id}")
    public String editRoute(@PathVariable Long id,
                              @RequestParam String routeName,
                              @RequestParam String origin,
                              @RequestParam String destination,
                              @RequestParam(required = false) Double distanceKm,
                              HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }

        try {
            routeService.updateRoute(id, routeName, origin, destination, distanceKm);
            ra.addFlashAttribute("success", "Route updated successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/routes";
    }

    @GetMapping("/routes/delete/{id}")
    public String deleteRoute(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        routeService.deleteRoute(id);
        ra.addFlashAttribute("success", "Route deleted.");
        return "redirect:/admin/routes";
    }

    // ===================== SCHEDULE CRUD =====================

    @GetMapping("/schedules")
    public String listSchedules(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        model.addAttribute("schedules", scheduleService.findAll());
        return "admin-schedules";
    }

    @GetMapping("/schedules/add")
    public String showAddScheduleForm(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        model.addAttribute("buses", busService.findAll());
        model.addAttribute("routes", routeService.findAll());
        return "admin-schedule-form";
    }

    @PostMapping("/schedules/add")
    public String addSchedule(@RequestParam Long busId,
                                @RequestParam Long routeId,
                                @RequestParam String departureTime,
                                @RequestParam String arrivalTime,
                                @RequestParam double fare,
                                HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }

        try {
            LocalDateTime departure = LocalDateTime.parse(departureTime);
            LocalDateTime arrival = LocalDateTime.parse(arrivalTime);
            scheduleService.createSchedule(busId, routeId, departure, arrival, fare);
            ra.addFlashAttribute("success", "Schedule added successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/schedules";
    }

    @GetMapping("/schedules/edit/{id}")
    public String showEditScheduleForm(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        model.addAttribute("schedule", scheduleService.findById(id));
        model.addAttribute("buses", busService.findAll());
        model.addAttribute("routes", routeService.findAll());
        model.addAttribute("mementos", scheduleService.getMementos(id));
        return "admin-schedule-form";
    }

    @PostMapping("/schedules/edit/{id}")
    public String editSchedule(@PathVariable Long id,
                                 @RequestParam Long busId,
                                 @RequestParam Long routeId,
                                 @RequestParam String departureTime,
                                 @RequestParam String arrivalTime,
                                 @RequestParam double fare,
                                 @RequestParam int availableSeats,
                                 @RequestParam String status,
                                 HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }

        try {
            LocalDateTime departure = LocalDateTime.parse(departureTime);
            LocalDateTime arrival = LocalDateTime.parse(arrivalTime);
            // MEMENTO PATTERN: State is auto-saved before update in ScheduleService
            scheduleService.updateSchedule(id, busId, routeId, departure, arrival, fare, availableSeats, status);
            ra.addFlashAttribute("success", "Schedule updated! Previous state saved for revert.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/schedules";
    }

    // MEMENTO PATTERN: Revert schedule to previous state
    @GetMapping("/schedules/revert/{id}")
    public String revertSchedule(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        try {
            scheduleService.revertToLastState(id);
            ra.addFlashAttribute("success", "Schedule reverted to previous state!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/schedules";
    }

    @GetMapping("/schedules/delete/{id}")
    public String deleteSchedule(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        scheduleService.deleteSchedule(id);
        ra.addFlashAttribute("success", "Schedule deleted.");
        return "redirect:/admin/schedules";
    }

    // ===================== VIEW ALL BOOKINGS =====================

    @GetMapping("/bookings")
    public String listBookings(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isAdmin(session)) { ra.addFlashAttribute("error", "Admin access only."); return "redirect:/login"; }
        model.addAttribute("bookings", bookingService.findAll());
        return "admin-bookings";
    }
}
