package com.busticket.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * ScheduleMemento entity - Memento Pattern.
 * Stores a snapshot of a Schedule before edits, allowing "revert" functionality.
 */
@Entity
@Table(name = "schedule_mementos")
public class ScheduleMemento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @Column(name = "bus_id", nullable = false)
    private Long busId;

    @Column(name = "route_id", nullable = false)
    private Long routeId;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(nullable = false)
    private double fare;

    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    @Column(length = 20)
    private String status;

    @Column(name = "saved_at")
    private LocalDateTime savedAt = LocalDateTime.now();

    // --- Constructors ---
    public ScheduleMemento() {}

    /**
     * Create a memento from the current state of a Schedule.
     */
    public ScheduleMemento(Schedule schedule) {
        this.scheduleId = schedule.getId();
        this.busId = schedule.getBus().getId();
        this.routeId = schedule.getRoute().getId();
        this.departureTime = schedule.getDepartureTime();
        this.arrivalTime = schedule.getArrivalTime();
        this.fare = schedule.getFare();
        this.availableSeats = schedule.getAvailableSeats();
        this.status = schedule.getStatus();
        this.savedAt = LocalDateTime.now();
    }

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }

    public Long getBusId() { return busId; }
    public void setBusId(Long busId) { this.busId = busId; }

    public Long getRouteId() { return routeId; }
    public void setRouteId(Long routeId) { this.routeId = routeId; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSavedAt() { return savedAt; }
    public void setSavedAt(LocalDateTime savedAt) { this.savedAt = savedAt; }
}
