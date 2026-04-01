package com.busticket.repository;

import com.busticket.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.route.origin = :origin " +
           "AND s.route.destination = :destination " +
           "AND s.departureTime >= :fromDate " +
           "AND s.departureTime < :toDate " +
           "AND s.status = 'ACTIVE' " +
           "AND s.availableSeats > 0")
    List<Schedule> searchSchedules(@Param("origin") String origin,
                                   @Param("destination") String destination,
                                   @Param("fromDate") LocalDateTime fromDate,
                                   @Param("toDate") LocalDateTime toDate);

    List<Schedule> findByStatus(String status);
}
