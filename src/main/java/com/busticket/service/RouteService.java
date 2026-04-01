package com.busticket.service;

import com.busticket.entity.Route;
import com.busticket.entity.Stop;
import com.busticket.repository.RouteRepository;
import com.busticket.repository.StopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RouteService - Manages routes and stops.
 * Leverages the COMPOSITE PATTERN: routes can have sub-routes and stops.
 */
@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;

    public RouteService(RouteRepository routeRepository, StopRepository stopRepository) {
        this.routeRepository = routeRepository;
        this.stopRepository = stopRepository;
    }

    public Route createRoute(String routeName, String origin, String destination, Double distanceKm) {
        Route route = new Route(routeName, origin, destination, distanceKm);
        return routeRepository.save(route);
    }

    public Route addSubRoute(Long parentId, String routeName, String origin, String destination, Double distanceKm) {
        Route parent = findById(parentId);
        Route subRoute = new Route(routeName, origin, destination, distanceKm);
        parent.addSubRoute(subRoute);
        routeRepository.save(subRoute);
        return parent;
    }

    public Stop addStop(Long routeId, String stopName, int stopOrder) {
        Route route = findById(routeId);
        Stop stop = new Stop(stopName, stopOrder);
        stop.setRoute(route);
        return stopRepository.save(stop);
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public List<Route> findTopLevelRoutes() {
        return routeRepository.findByParentRouteIsNull();
    }

    public Route findById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + id));
    }

    public List<Stop> findStopsByRoute(Long routeId) {
        return stopRepository.findByRouteIdOrderByStopOrder(routeId);
    }

    public Route updateRoute(Long id, String routeName, String origin, String destination, Double distanceKm) {
        Route route = findById(id);
        route.setRouteName(routeName);
        route.setOrigin(origin);
        route.setDestination(destination);
        route.setDistanceKm(distanceKm);
        return routeRepository.save(route);
    }

    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }
}
