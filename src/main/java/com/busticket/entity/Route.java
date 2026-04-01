package com.busticket.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Route entity - Implements the Composite Pattern.
 * A main route can have sub-routes (children) and multiple stops.
 */
@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "route_name", nullable = false, length = 150)
    private String routeName;

    @Column(nullable = false, length = 100)
    private String origin;

    @Column(nullable = false, length = 100)
    private String destination;

    @Column(name = "distance_km")
    private Double distanceKm;

    // --- Composite Pattern: Parent-Child ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_route_id")
    private Route parentRoute;

    @OneToMany(mappedBy = "parentRoute", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Route> subRoutes = new ArrayList<>();

    // --- Stops for this route ---
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stop> stops = new ArrayList<>();

    // --- Constructors ---
    public Route() {}

    public Route(String routeName, String origin, String destination, Double distanceKm) {
        this.routeName = routeName;
        this.origin = origin;
        this.destination = destination;
        this.distanceKm = distanceKm;
    }

    // --- Composite helper methods ---
    public void addSubRoute(Route subRoute) {
        subRoute.setParentRoute(this);
        this.subRoutes.add(subRoute);
    }

    public void addStop(Stop stop) {
        stop.setRoute(this);
        this.stops.add(stop);
    }

    /**
     * Composite Pattern: Calculate total distance including sub-routes.
     */
    public double getTotalDistance() {
        double total = (this.distanceKm != null) ? this.distanceKm : 0;
        for (Route sub : subRoutes) {
            total += sub.getTotalDistance();
        }
        return total;
    }

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Route getParentRoute() { return parentRoute; }
    public void setParentRoute(Route parentRoute) { this.parentRoute = parentRoute; }

    public List<Route> getSubRoutes() { return subRoutes; }
    public void setSubRoutes(List<Route> subRoutes) { this.subRoutes = subRoutes; }

    public List<Stop> getStops() { return stops; }
    public void setStops(List<Stop> stops) { this.stops = stops; }
}
