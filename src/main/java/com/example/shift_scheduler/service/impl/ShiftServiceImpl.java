// src/main/java/com/example/shift_scheduler/service/impl/ShiftServiceImpl.java
package com.example.shift_scheduler.service.impl;

import com.example.shift_scheduler.model.Shift;
import com.example.shift_scheduler.repository.ShiftRepository;
import com.example.shift_scheduler.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the ShiftService interface.
 * Provides the business logic for shift management.
 */
@Service
@Transactional
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;

    @Autowired
    public ShiftServiceImpl(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @Override
    public Shift createShift(Shift shift) {
        validateShift(shift);
        return shiftRepository.save(shift);
    }

    @Override
    @Transactional(readOnly = true)
    public Shift getShiftById(Long id) {
        return shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found with id: " + id));
    }

    @Override
    public Shift updateShift(Long id, Shift shiftDetails) {
        Shift shift = getShiftById(id);

        // Update the shift properties
        shift.setName(shiftDetails.getName());
        shift.setStartTime(shiftDetails.getStartTime());
        shift.setEndTime(shiftDetails.getEndTime());
        shift.setDayOfWeek(shiftDetails.getDayOfWeek());

        validateShift(shift);
        return shiftRepository.save(shift);
    }

    @Override
    public void deleteShift(Long id) {
        if (!shiftRepository.existsById(id)) {
            throw new RuntimeException("Shift not found with id: " + id);
        }
        shiftRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shift> getShiftsByDay(Integer dayOfWeek) {
        if (dayOfWeek < 1 || dayOfWeek > 7) {
            throw new IllegalArgumentException("Day of week must be between 1 and 7");
        }
        return shiftRepository.findByDayOfWeek(dayOfWeek);
    }

    /**
     * Validates shift data before saving
     *
     * @param shift the shift to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateShift(Shift shift) {
        if (shift.getStartTime() == null || shift.getEndTime() == null) {
            throw new IllegalArgumentException("Start time and end time must not be null");
        }

        if (shift.getStartTime().equals(shift.getEndTime())) {
            throw new IllegalArgumentException("Start time and end time cannot be the same");
        }

        if (shift.getDayOfWeek() < 1 || shift.getDayOfWeek() > 7) {
            throw new IllegalArgumentException("Day of week must be between 1 and 7");
        }

        // Check for overlapping shifts on the same day
        List<Shift> existingShifts = shiftRepository.findByDayOfWeek(shift.getDayOfWeek());
        for (Shift existingShift : existingShifts) {
            if (shift.getId() != null && shift.getId().equals(existingShift.getId())) {
                continue; // Skip comparison with itself during update
            }
            if (shift.overlapsWith(existingShift)) {
                throw new IllegalArgumentException("Shift overlaps with existing shift: " + existingShift.getName());
            }
        }
    }
}