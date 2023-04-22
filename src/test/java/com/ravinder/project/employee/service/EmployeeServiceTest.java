package com.ravinder.project.employee.service;

import com.ravinder.project.employee.exception.ResourceExistException;
import com.ravinder.project.employee.exception.ResourceNotFoundException;
import com.ravinder.project.employee.model.Employee;
import com.ravinder.project.employee.repository.EmployeeRepository;
import com.ravinder.project.employee.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//add Mockito extension to support mock and injectmocks features
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
//        employeeRepository = Mockito.mock(EmployeeRepository.class); with @Mock now
//        employeeService = new EmployeeServiceImpl(employeeRepository); with @Inject mocks now
         employee = Employee.builder()
                .id(1L)
                .firstName("Ravinder")
                .lastName("Singh")
                .email("ravinder@gmail.com")
                .build();
    }

    @Test
    public void givenEmployee_whenSaveEmployee_thenReturnEmployee(){
        //given - setup or precondition
        BDDMockito
                .given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        BDDMockito
                .given(employeeRepository.save(employee)).willReturn(employee);
        //when
        Employee savedEmployee = employeeService.saveEmployee(employee);
        //then
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void givenEmployee_whenSaveEmployee_thenThrowException(){
        //given - setup or precondition
        BDDMockito
                .given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));
//        unnecessary stubbing
//        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
        //when
        org.junit.jupiter.api.Assertions
                .assertThrows(ResourceExistException.class, () -> employeeService.saveEmployee(employee));
        //then
        Mockito.verify(employeeRepository, Mockito.never()).save(Mockito.any(Employee.class));
    }

    @Test
    public void givenEmployeesList_whenGetAll_thenReturnEmployeesList(){
        //given
        BDDMockito
                .given(employeeRepository.findAll())
                .willReturn(List.of(
                        new Employee(2L, "Tony", "Stark", "tony@gmail.com"),
                        new Employee(3L, "Captatn", "America", "steeve@gmail.com")));
        //when
        List<Employee> employeeList = employeeService.getAllEmployees();
        //then
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    public void givenEmployeesList_whenGetAll_thenReturnEmptyList(){
        //given
        BDDMockito
                .given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        //when
        List<Employee> employeeList = employeeService.getAllEmployees();
        //then
        Assertions.assertThat(employeeList).isEmpty();
    }

    @Test
    public void givenEmployeeId_whenFindById_thenReturnEmployee(){
        //given
        BDDMockito
                .given(employeeRepository.findById(Mockito.any(Long.class))).willReturn(Optional.of(employee));
        //when
        Employee employeeById = employeeService.getEmployeeById(1L).get();
        //then
        Assertions.assertThat(employeeById).isNotNull();
    }

    @Test
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        //given
        BDDMockito
                .given(employeeRepository.findById(Mockito.any(Long.class))).willReturn(Optional.of(employee));
        employee.setFirstName("Rajesh");
        employee.setLastName("Kumar");
        BDDMockito
                .given(employeeRepository.save(employee))
                .willReturn(employee);
        //when
        Employee updatedEmployee = employeeService.updateEmployee(1L, employee);
        //then
        Assertions.assertThat(updatedEmployee).isNotNull();
        Assertions.assertThat(updatedEmployee.getFirstName()).isEqualTo("Rajesh");
        Assertions.assertThat(updatedEmployee.getLastName()).isEqualTo("Kumar");
    }

    @Test
    public void givenEmployee_whenUpdateEmployee_thenThrowsException(){
        //given
        BDDMockito
                .given(employeeRepository.findById(Mockito.any(Long.class)))
                .willThrow(new ResourceNotFoundException("Employee not found with id : "+ employee.getId()));

        //when
        org.junit.jupiter.api.Assertions
                .assertThrows(ResourceNotFoundException.class,
                        () -> employeeService.updateEmployee(Mockito.any(Long.class), employee));
        //then
        Mockito.verify(employeeRepository, Mockito.never()).save(Mockito.any(Employee.class));
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenThrowsException(){
        //given
        BDDMockito
                .given(employeeRepository.findById(Mockito.any(Long.class)))
                .willThrow(new ResourceNotFoundException("Employee not found with id : "+ employee.getId()));
        //when
        org.junit.jupiter.api.Assertions
                .assertThrows(ResourceNotFoundException.class,
                        () -> employeeService.deleteEmployee(Mockito.any(Long.class)));
        //then
        Mockito.verify(employeeRepository, Mockito.never()).deleteById(Mockito.any(Long.class));
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenDeleteEmployee(){
        //given
        BDDMockito
                .given(employeeRepository.findById(Mockito.any(Long.class))).willReturn(Optional.of(employee));
        BDDMockito
                .willDoNothing().given(employeeRepository).deleteById(Mockito.any(Long.class));
        //when
        employeeService.deleteEmployee(1L);
        //then
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(Mockito.any(Long.class));
    }




}