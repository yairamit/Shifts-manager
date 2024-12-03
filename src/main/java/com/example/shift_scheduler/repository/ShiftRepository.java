package com.example.shift_scheduler.repository;

import com.example.shift_scheduler.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalTime;
import java.util.List;

/**
 * Repository interface for managing Shift entities.
 * Provides CRUD operations and custom queries for shifts.
 */
@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    /**
     * Finds all shifts for a specific day of the week
     *
     * @param dayOfWeek the day to search for (1-7, where 1 is Sunday)
     * @return list of shifts on that day
     */
    List<Shift> findByDayOfWeek(Integer dayOfWeek);

    /**
     * Finds shifts that start between specific times
     *
     * @param startTime the earliest start time to include
     * @param endTime the latest start time to include
     * @return list of matching shifts
     */
    List<Shift> findByStartTimeBetween(LocalTime startTime, LocalTime endTime);

    /**
     * Finds shifts by name containing the given string (case-insensitive)
     *
     * @param name the name to search for
     * @return list of shifts with matching names
     */
    List<Shift> findByNameContainingIgnoreCase(String name);
}