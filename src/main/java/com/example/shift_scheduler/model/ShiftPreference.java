package com.example.shift_scheduler.model;


import lombok.Data;

/**
 * Represents an employee's preference for a specific shift.
 * Links employees with shifts and stores their preferences.
 *
 * @version 1.0
 * @since 2024-01-01
 */

import com.example.shift_scheduler.enums.PreferenceType;

import javax.persistence.*;

@Entity
@Table(name = "shift_preferences")
@Data
public class ShiftPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The employee who expressed this preference
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    /**
     * The shift this preference is for
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;

    /**
     * The type of preference expressed by the employee
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PreferenceType preferenceType;

    /**
     * Whether this is part of a sequence preference
     * (i.e., part of a series of consecutive shifts)
     */
    private boolean isPartOfSequence;

    /**
     * The sequence identifier (if part of a sequence)
     */
    private String sequenceId;
}

