// src/main/java/com/example/shift_scheduler/service/EmployeeService.java
package com.example.shift_scheduler.service;

import com.example.shift_scheduler.model.Employee;
import com.example.shift_scheduler.enums.EmployeeRole;
import java.util.List;

/**
 * Service interface for managing employees in the system.
 * Defines core business operations for employee management.
 */
public interface EmployeeService {

    /**
     * Creates a new employee
     * @param employee the employee to create
     * @return the saved employee with generated ID
     */
    Employee createEmployee(Employee employee);

    /**
     * Retrieves an employee by ID
     * @param id employee's ID
     * @return the found employee
     */
    Employee getEmployeeById(Long id);

    /**
     * Retrieves an employee by their unique employee ID
     * @param employeeId unique employee identifier
     * @return the found employee
     */
    Employee getEmployeeByEmployeeId(String employeeId);

    /**
     * Updates an existing employee's information
     * @param id employee's ID
     * @param employee updated employee details
     * @return the updated employee
     */
    Employee updateEmployee(Long id, Employee employee);

    /**
     * Deactivates an employee (soft delete)
     * @param id employee's ID
     */
    void deactivateEmployee(Long id);

    /**
     * Finds all active employees
     * @return list of active employees
     */
    List<Employee> getAllActiveEmployees();

    /**
     * Finds employees by their role
     * @param role the role to search for
     * @return list of employees with the specified role
     */
    List<Employee> getEmployeesByRole(EmployeeRole role);
}