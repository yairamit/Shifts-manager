package com.example.shift_scheduler.repository;

import com.example.shift_scheduler.enums.EmployeeRole;
import com.example.shift_scheduler.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Employee entities.
 * Provides CRUD operations and custom queries for employees.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Finds an employee by their unique employee ID
     *
     * @param employeeId the unique identifier to search for
     * @return optional containing the employee if found
     */
    Optional<Employee> findByEmployeeId(String employeeId);

    /**
     * Finds an employee by email address
     *
     * @param email the email to search for
     * @return optional containing the employee if found
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Finds all active employees
     *
     * @return list of active employees
     */
    List<Employee> findByIsActiveTrue();

    /**
     * Finds employees by role
     *
     * @param role the role to search for
     * @return list of employees with the specified role
     */
    List<Employee> findByRole(EmployeeRole role);

}