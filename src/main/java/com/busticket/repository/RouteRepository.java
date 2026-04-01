package com.busticket.repository;

import com.busticket.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByOriginAndDestination(String origin, String destination);
    List<Route> findByParentRouteIsNull(); // Only top-level routes
}
