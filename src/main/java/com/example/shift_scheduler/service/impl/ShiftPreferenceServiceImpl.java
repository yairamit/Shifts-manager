// src/main/java/com/example/shift_scheduler/service/impl/ShiftPreferenceServiceImpl.java
package com.example.shift_scheduler.service.impl;

import com.example.shift_scheduler.model.ShiftPreference;
import com.example.shift_scheduler.repository.ShiftPreferenceRepository;
import com.example.shift_scheduler.service.ShiftPreferenceService;
import com.example.shift_scheduler.service.EmployeeService;
import com.example.shift_scheduler.service.ShiftService;
import com.example.shift_scheduler.enums.PreferenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the ShiftPreferenceService interface.
 * Provides business logic for managing shift preferences.
 */
@Service
@Transactional
public class ShiftPreferenceServiceImpl implements ShiftPreferenceService {

    private final ShiftPreferenceRepository preferenceRepository;
    private final EmployeeService employeeService;
    private final ShiftService shiftService;

    @Autowired
    public ShiftPreferenceServiceImpl(
            ShiftPreferenceRepository preferenceRepository,
            EmployeeService employeeService,
            ShiftService shiftService) {
        this.preferenceRepository = preferenceRepository;
        this.employeeService = employeeService;
        this.shiftService = shiftService;
    }

    @Override
    public ShiftPreference createPreference(ShiftPreference preference) {
        validatePreference(preference);

        // Check if employee has reached the maximum number of UNAVAILABLE preferences
        if (preference.getPreferenceType() == PreferenceType.UNAVAILABLE) {
            long unavailableCount = preferenceRepository
                    .countUnavailablePreferences(preference.getEmployee().getId());
            if (unavailableCount >= 7) {
                throw new IllegalStateException("Employee has reached maximum number of unavailable shifts");
            }
        }

        return preferenceRepository.save(preference);
    }

    @Override
    public List<ShiftPreference> createSequencePreferences(List<ShiftPreference> preferences) {
        if (preferences.size() < 2) {
            throw new IllegalArgumentException("Sequence must contain at least 2 preferences");
        }

        String sequenceId = UUID.randomUUID().toString();
        preferences.forEach(pref -> {
            pref.setPartOfSequence(true);
            pref.setSequenceId(sequenceId);
            validatePreference(pref);
        });

        return preferenceRepository.saveAll(preferences);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShiftPreference> getPreferencesByEmployee(Long employeeId) {
        // Verify employee exists
        employeeService.getEmployeeById(employeeId);
        return preferenceRepository.findByEmployeeId(employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShiftPreference> getPreferencesByShift(Long shiftId) {
        // Verify shift exists
        shiftService.getShiftById(shiftId);
        return preferenceRepository.findByShiftId(shiftId);
    }

    @Override
    public ShiftPreference updatePreference(Long id, ShiftPreference preferenceDetails) {
        ShiftPreference existingPreference = preferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Preference not found with id: " + id));

        // Update only allowed fields
        existingPreference.setPreferenceType(preferenceDetails.getPreferenceType());

        validatePreference(existingPreference);
        return preferenceRepository.save(existingPreference);
    }

    /**
     * Validates a shift preference
     * @param preference the preference to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validatePreference(ShiftPreference preference) {
        if (preference.getEmployee() == null || preference.getEmployee().getId() == null) {
            throw new IllegalArgumentException("Employee is required");
        }

        if (preference.getShift() == null || preference.getShift().getId() == null) {
            throw new IllegalArgumentException("Shift is required");
        }

        // Verify that both employee and shift exist
        employeeService.getEmployeeById(preference.getEmployee().getId());
        shiftService.getShiftById(preference.getShift().getId());

        // Check for duplicate preferences
        List<ShiftPreference> existingPreferences = preferenceRepository
                .findByEmployeeIdAndShiftId(
                        preference.getEmployee().getId(),
                        preference.getShift().getId()
                );

        if (!existingPreferences.isEmpty() &&
                (preference.getId() == null ||
                        !preference.getId().equals(existingPreferences.get(0).getId()))) {
            throw new IllegalArgumentException("Preference already exists for this employee and shift");
        }
    }
}
