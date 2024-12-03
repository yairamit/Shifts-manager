package com.example.shift_scheduler.repository;

import com.example.shift_scheduler.enums.PreferenceType;
import com.example.shift_scheduler.model.ShiftPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for managing ShiftPreference entities.
 * Provides CRUD operations and custom queries for shift preferences.
 */
@Repository
public interface ShiftPreferenceRepository extends JpaRepository<ShiftPreference, Long> {

    /**
     * Finds all preferences for a specific employee
     *
     * @param employeeId ID of the employee
     * @return list of shift preferences
     */
    List<ShiftPreference> findByEmployeeId(Long employeeId);

    /**
     * Finds all preferences for a specific shift
     *
     * @param shiftId ID of the shift
     * @return list of shift preferences
     */
    List<ShiftPreference> findByShiftId(Long shiftId);

    /**
     * Finds preferences by type for a specific employee
     *
     * @param employeeId ID of the employee
     * @param preferenceType type of preference
     * @return list of matching preferences
     */
    List<ShiftPreference> findByEmployeeIdAndPreferenceType(Long employeeId, PreferenceType preferenceType);

    /**
     * Finds all sequence preferences for an employee
     *
     * @param employeeId ID of the employee
     * @return list of preferences that are part of sequences
     */
    List<ShiftPreference> findByEmployeeIdAndIsPartOfSequenceTrue(Long employeeId);

    /**
     * Counts how many unavailable preferences an employee has
     *
     * @param employeeId ID of the employee
     * @return count of unavailable preferences
     */

    List<ShiftPreference> findByEmployeeIdAndShiftId(Long employeeId, Long shiftId);
    @Query("SELECT COUNT(sp) FROM ShiftPreference sp " +
            "WHERE sp.employee.id = ?1 AND sp.preferenceType = 'UNAVAILABLE'")
    long countUnavailablePreferences(Long employeeId);
}