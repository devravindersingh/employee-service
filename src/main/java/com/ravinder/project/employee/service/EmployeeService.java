package com.ravinder.project.employee.service;

import com.ravinder.project.employee.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(Long employeeId);
    Employee updateEmployee(Long employeeId, Employee employee);
    void deleteEmployee(Long employeeId);


}
