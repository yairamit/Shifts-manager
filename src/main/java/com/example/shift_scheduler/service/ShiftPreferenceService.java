// src/main/java/com/example/shift_scheduler/service/ShiftPreferenceService.java
package com.example.shift_scheduler.service;

import com.example.shift_scheduler.model.ShiftPreference;
import com.example.shift_scheduler.enums.PreferenceType;
import java.util.List;

/**
 * Service interface for managing shift preferences in the system.
 * Handles employee preferences for specific shifts.
 */
public interface ShiftPreferenceService {

    /**
     * Creates a new shift preference
     * @param shiftPreference the preference to create
     * @return the saved preference
     */
    ShiftPreference createPreference(ShiftPreference shiftPreference);

    /**
     * Creates multiple preferences as a sequence
     * @param preferences list of preferences that form a sequence
     * @return list of saved preferences
     */
    List<ShiftPreference> createSequencePreferences(List<ShiftPreference> preferences);

    /**
     * Gets all preferences for a specific employee
     * @param employeeId the employee's ID
     * @return list of preferences
     */
    List<ShiftPreference> getPreferencesByEmployee(Long employeeId);

    /**
     * Gets all preferences for a specific shift
     * @param shiftId the shift's ID
     * @return list of preferences
     */
    List<ShiftPreference> getPreferencesByShift(Long shiftId);

    /**
     * Updates an existing preference
     * @param id preference ID
     * @param preference updated preference details
     * @return updated preference
     */
    ShiftPreference updatePreference(Long id, ShiftPreference preference);
}