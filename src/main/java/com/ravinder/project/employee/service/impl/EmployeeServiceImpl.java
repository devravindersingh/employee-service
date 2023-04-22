package com.ravinder.project.employee.service.impl;

import com.ravinder.project.employee.exception.ResourceExistException;
import com.ravinder.project.employee.exception.ResourceNotFoundException;
import com.ravinder.project.employee.model.Employee;
import com.ravinder.project.employee.repository.EmployeeRepository;
import com.ravinder.project.employee.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> checkEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (checkEmployee.isPresent())
            throw new ResourceExistException("Employee already exist with given email:" + employee.getEmail());
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Employee updateEmployee(Long employeeId, Employee updateEmployee) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + employeeId));

        employee.setFirstName(updateEmployee.getFirstName());
        employee.setLastName(updateEmployee.getLastName());
        employee.setEmail(updateEmployee.getEmail());

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + employeeId));
        employeeRepository.deleteById(employeeId);
    }
}
