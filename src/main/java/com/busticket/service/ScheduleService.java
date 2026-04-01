package com.busticket.service;

import com.busticket.entity.Bus;
import com.busticket.entity.Route;
import com.busticket.entity.Schedule;
import com.busticket.entity.ScheduleMemento;
import com.busticket.repository.BookingRepository;
import com.busticket.repository.BusRepository;
import com.busticket.repository.RouteRepository;
import com.busticket.repository.ScheduleMementoRepository;
import com.busticket.repository.ScheduleRepository;
import com.busticket.pattern.iterator.SeatCollection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ScheduleService - Manages schedules.
 * Integrates MEMENTO PATTERN for saving/restoring schedule states.
 * Integrates ITERATOR PATTERN for seat traversal.
 */
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final BookingRepository bookingRepository;
    private final ScheduleMementoRepository mementoRepository;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           BusRepository busRepository,
                           RouteRepository routeRepository,
                           BookingRepository bookingRepository,
                           ScheduleMementoRepository mementoRepository) {
        this.scheduleRepository = scheduleRepository;
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.bookingRepository = bookingRepository;
        this.mementoRepository = mementoRepository;
    }

    /**
     * Create a new schedule.
     */
    public Schedule createSchedule(Long busId, Long routeId,
                                    LocalDateTime departureTime, LocalDateTime arrivalTime,
                                    double fare) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        Schedule schedule = new Schedule();
        schedule.setBus(bus);
        schedule.setRoute(route);
        schedule.setDepartureTime(departureTime);
        schedule.setArrivalTime(arrivalTime);
        schedule.setFare(fare);
        schedule.setAvailableSeats(bus.getTotalSeats());
        schedule.setStatus("ACTIVE");

        return scheduleRepository.save(schedule);
    }

    /**
     * MEMENTO PATTERN: Save the current state of a schedule before editing.
     */
    public ScheduleMemento saveState(Long scheduleId) {
        Schedule schedule = findById(scheduleId);
        ScheduleMemento memento = new ScheduleMemento(schedule);
        return mementoRepository.save(memento);
    }

    /**
     * MEMENTO PATTERN: Revert a schedule to its last saved state.
     */
    @Transactional
    public Schedule revertToLastState(Long scheduleId) {
        Optional<ScheduleMemento> optMemento =
                mementoRepository.findTopByScheduleIdOrderBySavedAtDesc(scheduleId);

        if (optMemento.isEmpty()) {
            throw new RuntimeException("No saved state found for schedule #" + scheduleId);
        }

        ScheduleMemento memento = optMemento.get();
        Schedule schedule = findById(scheduleId);

        // Restore state from memento
        Bus bus = busRepository.findById(memento.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        Route route = routeRepository.findById(memento.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));

        schedule.setBus(bus);
        schedule.setRoute(route);
        schedule.setDepartureTime(memento.getDepartureTime());
        schedule.setArrivalTime(memento.getArrivalTime());
        schedule.setFare(memento.getFare());
        schedule.setAvailableSeats(memento.getAvailableSeats());
        schedule.setStatus(memento.getStatus());

        // Remove the used memento
        mementoRepository.delete(memento);

        return scheduleRepository.save(schedule);
    }

    /**
     * Update a schedule (auto-saves state via Memento before update).
     */
    @Transactional
    public Schedule updateSchedule(Long id, Long busId, Long routeId,
                                    LocalDateTime departureTime, LocalDateTime arrivalTime,
                                    double fare, int availableSeats, String status) {
        // Save state before editing (Memento Pattern)
        saveState(id);

        Schedule schedule = findById(id);
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        schedule.setBus(bus);
        schedule.setRoute(route);
        schedule.setDepartureTime(departureTime);
        schedule.setArrivalTime(arrivalTime);
        schedule.setFare(fare);
        schedule.setAvailableSeats(availableSeats);
        schedule.setStatus(status);

        return scheduleRepository.save(schedule);
    }

    /**
     * Search schedules by origin, destination, and date.
     */
    public List<Schedule> searchSchedules(String origin, String destination, LocalDate date) {
        LocalDateTime fromDate = date.atStartOfDay();
        LocalDateTime toDate = date.plusDays(1).atStartOfDay();
        return scheduleRepository.searchSchedules(origin, destination, fromDate, toDate);
    }

    /**
     * ITERATOR PATTERN: Get available seats as a SeatCollection.
     * Returns seat numbers that are NOT yet booked.
     */
    public SeatCollection getAvailableSeats(Long scheduleId) {
        Schedule schedule = findById(scheduleId);
        int totalSeats = schedule.getBus().getTotalSeats();

        // Get all booked seat numbers for this schedule
        List<Integer> bookedSeats = bookingRepository.findByScheduleId(scheduleId)
                .stream()
                .map(b -> b.getSeatNumber())
                .toList();

        // Build a list of available seats
        List<Integer> available = new ArrayList<>();
        for (int i = 1; i <= totalSeats; i++) {
            if (!bookedSeats.contains(i)) {
                available.add(i);
            }
        }

        return new SeatCollection(available);
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public Schedule findById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    public List<ScheduleMemento> getMementos(Long scheduleId) {
        return mementoRepository.findByScheduleIdOrderBySavedAtDesc(scheduleId);
    }
}
