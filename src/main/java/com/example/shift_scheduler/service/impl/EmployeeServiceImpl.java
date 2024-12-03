// src/main/java/com/example/shift_scheduler/service/impl/EmployeeServiceImpl.java
package com.example.shift_scheduler.service.impl;

import com.example.shift_scheduler.model.Employee;
import com.example.shift_scheduler.repository.EmployeeRepository;
import com.example.shift_scheduler.service.EmployeeService;
import com.example.shift_scheduler.enums.EmployeeRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the EmployeeService interface.
 * Provides the business logic for employee management.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        validateNewEmployee(employee);
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeByEmployeeId(String employeeId) {
        return employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with employeeId: " + employeeId));
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = getEmployeeById(id);

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhoneNumber(employeeDetails.getPhoneNumber());
        employee.setMaxShiftsPerWeek(employeeDetails.getMaxShiftsPerWeek());
        employee.setMinRestHours(employeeDetails.getMinRestHours());

        validateExistingEmployee(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public void deactivateEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllActiveEmployees() {
        return employeeRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesByRole(EmployeeRole role) {
        return employeeRepository.findByRole(role);
    }

    /**
     * Validates a new employee before saving
     * @param employee the employee to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateNewEmployee(Employee employee) {
        // Check for required fields
        if (employee.getEmployeeId() == null || employee.getEmployeeId().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID is required");
        }

        // Check if employee ID is already in use
        employeeRepository.findByEmployeeId(employee.getEmployeeId())
                .ifPresent(e -> {
                    throw new IllegalArgumentException("Employee ID already exists");
                });

        // Check if email is already in use
        employeeRepository.findByEmail(employee.getEmail())
                .ifPresent(e -> {
                    throw new IllegalArgumentException("Email already in use");
                });

        validateCommonFields(employee);
    }

    /**
     * Validates an existing employee before updating
     * @param employee the employee to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateExistingEmployee(Employee employee) {
        // Check for email uniqueness (excluding current employee)
        employeeRepository.findByEmail(employee.getEmail())
                .ifPresent(e -> {
                    if (!e.getId().equals(employee.getId())) {
                        throw new IllegalArgumentException("Email already in use");
                    }
                });

        validateCommonFields(employee);
    }

    /**
     * Validates fields common to both new and existing employees
     * @param employee the employee to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateCommonFields(Employee employee) {
        if (employee.getMaxShiftsPerWeek() != null && employee.getMaxShiftsPerWeek() < 0) {
            throw new IllegalArgumentException("Maximum shifts per week cannot be negative");
        }

        if (employee.getMinRestHours() != null && employee.getMinRestHours() < 0) {
            throw new IllegalArgumentException("Minimum rest hours cannot be negative");
        }
    }
}