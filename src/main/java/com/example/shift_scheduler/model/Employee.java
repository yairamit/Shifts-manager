package com.example.shift_scheduler.model;

import com.example.shift_scheduler.enums.EmployeeRole;

import lombok.Data;

import javax.persistence.*;

/**
 * Represents an employee in the scheduling system.
 * Contains employee's personal information and work-related settings.
 *
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "employees")
@Data
public class Employee{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Employee's unique identifier/username in the system
     */
    @Column(unique = true, nullable = false)
    private String employeeId;

    /**
     * Employee's first name
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * Employee's last name
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * Employee's email address
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Employee's phone number
     */
    @Column(nullable = false)
    private String phoneNumber;

    /**
     * Maximum number of shifts per week for this employee
     */
    @Column(name = "max_shifts_per_week")
    private Integer maxShiftsPerWeek;

    /**
     * Minimum rest hours required between shifts
     */
    @Column(name = "min_rest_hours")
    private Integer minRestHours;

    /**
     * Whether the employee is active in the system
     */
    @Column(nullable = false)
    private boolean isActive = true;

    /**
     * The employee's role in the system (e.g., ADMIN, USER)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeRole role = EmployeeRole.USER;

    /**
     * Gets the employee's full name
     *
     * @return concatenated first and last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}

