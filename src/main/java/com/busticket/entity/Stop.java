package com.busticket.entity;

import jakarta.persistence.*;

/**
 * Stop entity - Represents a sub-stop within a Route.
 * Part of the Composite Pattern for route management.
 */
@Entity
@Table(name = "stops")
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(name = "stop_name", nullable = false, length = 100)
    private String stopName;

    @Column(name = "stop_order", nullable = false)
    private int stopOrder;

    // --- Constructors ---
    public Stop() {}

    public Stop(String stopName, int stopOrder) {
        this.stopName = stopName;
        this.stopOrder = stopOrder;
    }

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    public String getStopName() { return stopName; }
    public void setStopName(String stopName) { this.stopName = stopName; }

    public int getStopOrder() { return stopOrder; }
    public void setStopOrder(int stopOrder) { this.stopOrder = stopOrder; }
}
