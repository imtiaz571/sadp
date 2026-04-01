package com.busticket.repository;

import com.busticket.entity.ScheduleMemento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleMementoRepository extends JpaRepository<ScheduleMemento, Long> {
    List<ScheduleMemento> findByScheduleIdOrderBySavedAtDesc(Long scheduleId);
    Optional<ScheduleMemento> findTopByScheduleIdOrderBySavedAtDesc(Long scheduleId);
}
