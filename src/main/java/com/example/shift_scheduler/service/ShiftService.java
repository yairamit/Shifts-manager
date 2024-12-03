// src/main/java/com/example/shift_scheduler/service/ShiftService.java
package com.example.shift_scheduler.service;

import com.example.shift_scheduler.model.Shift;
import java.time.LocalTime;
import java.util.List;

/**
 * Service interface for managing shifts in the system.
 * Defines core business operations for shift management.
 */
public interface ShiftService {

    /**
     * Creates a new shift in the system
     *
     * @param shift the shift to create
     * @return the created shift with generated ID
     * @throws IllegalArgumentException if shift timing is invalid
     */
    Shift createShift(Shift shift);

    /**
     * Retrieves a shift by its ID
     *
     * @param id the shift ID
     * @return the shift if found
     * @throws RuntimeException if shift not found
     */
    Shift getShiftById(Long id);

    /**
     * Updates an existing shift
     *
     * @param id the ID of the shift to update
     * @param shift the new shift details
     * @return the updated shift
     * @throws RuntimeException if shift not found
     */
    Shift updateShift(Long id, Shift shift);

    /**
     * Deletes a shift by its ID
     *
     * @param id the ID of the shift to delete
     * @throws RuntimeException if shift not found
     */
    void deleteShift(Long id);

    /**
     * Finds all shifts for a specific day
     *
     * @param dayOfWeek the day to search for (1-7)
     * @return list of shifts on that day
     */
    List<Shift> getShiftsByDay(Integer dayOfWeek);
}