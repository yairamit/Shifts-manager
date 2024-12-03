package com.example.shift_scheduler.controller;

import com.example.shift_scheduler.model.Shift;
import com.example.shift_scheduler.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for managing shifts.
 * Provides endpoints for CRUD operations on shifts.
 */
@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    private final ShiftService shiftService;

    @Autowired
    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    /**
     * Creates a new shift
     */
    @PostMapping
    public ResponseEntity<Shift> createShift(@RequestBody Shift shift) {
        Shift createdShift = shiftService.createShift(shift);
        return ResponseEntity.ok(createdShift);
    }

    /**
     * Retrieves a shift by its ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Shift> getShift(@PathVariable Long id) {
        Shift shift = shiftService.getShiftById(id);
        return ResponseEntity.ok(shift);
    }

    /**
     * Updates an existing shift
     */
    @PutMapping("/{id}")
    public ResponseEntity<Shift> updateShift(@PathVariable Long id, @RequestBody Shift shift) {
        Shift updatedShift = shiftService.updateShift(id, shift);
        return ResponseEntity.ok(updatedShift);
    }

    /**
     * Deletes a shift
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long id) {
        shiftService.deleteShift(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Gets all shifts for a specific day
     */
    @GetMapping("/day/{dayOfWeek}")
    public ResponseEntity<List<Shift>> getShiftsByDay(@PathVariable Integer dayOfWeek) {
        List<Shift> shifts = shiftService.getShiftsByDay(dayOfWeek);
        return ResponseEntity.ok(shifts);
    }
}