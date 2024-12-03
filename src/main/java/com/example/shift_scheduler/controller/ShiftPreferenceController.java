package com.example.shift_scheduler.controller;

import com.example.shift_scheduler.model.ShiftPreference;
import com.example.shift_scheduler.service.ShiftPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing shift preferences.
 * Provides endpoints for handling employee shift preferences.
 */
@RestController
@RequestMapping("/api/preferences")
public class ShiftPreferenceController {

    private final ShiftPreferenceService preferenceService;

    @Autowired
    public ShiftPreferenceController(ShiftPreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    /**
     * Creates a new shift preference
     */
    @PostMapping
    public ResponseEntity<ShiftPreference> createPreference(@RequestBody ShiftPreference preference) {
        return ResponseEntity.ok(preferenceService.createPreference(preference));
    }

    /**
     * Creates a sequence of preferences
     */
    @PostMapping("/sequence")
    public ResponseEntity<List<ShiftPreference>> createSequence(@RequestBody List<ShiftPreference> preferences) {
        return ResponseEntity.ok(preferenceService.createSequencePreferences(preferences));
    }

    /**
     * Gets all preferences for a specific employee
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ShiftPreference>> getEmployeePreferences(@PathVariable Long employeeId) {
        return ResponseEntity.ok(preferenceService.getPreferencesByEmployee(employeeId));
    }

    /**
     * Gets all preferences for a specific shift
     */
    @GetMapping("/shift/{shiftId}")
    public ResponseEntity<List<ShiftPreference>> getShiftPreferences(@PathVariable Long shiftId) {
        return ResponseEntity.ok(preferenceService.getPreferencesByShift(shiftId));
    }

    /**
     * Updates an existing preference
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShiftPreference> updatePreference(
            @PathVariable Long id,
            @RequestBody ShiftPreference preference) {
        return ResponseEntity.ok(preferenceService.updatePreference(id, preference));
    }
}