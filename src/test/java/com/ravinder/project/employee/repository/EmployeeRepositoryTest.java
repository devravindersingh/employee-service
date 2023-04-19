package com.ravinder.project.employee.repository;

import com.ravinder.project.employee.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Ravinder")
                .lastName("Singh")
                .email("ravinder@gmail.com")
                .build();
    }

    //Junit test for save employee operation
    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployee_whenSave_thenReturnSavedEmployee(){

        //given - precondition or setup - already done in BeforeEach
        //when - action or behaviour
        Employee savedEmployee = employeeRepository.save(employee);
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void givenEmployeeList_whenFindAll_thenReturnEmployeeList(){
        //given
        employeeRepository.save(employee);
        Employee employee1 = Employee.builder()
                .firstName("Vishal")
                .lastName("Kumar")
                .email("vishal@gmail.com").build();
        employeeRepository.save(employee1);
        //when
        List<Employee> employeeList = employeeRepository.findAll();
        //then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    @Test
    public void givenEmployeeId_whenFindById_thenReturnEmployee(){
        //given
        employeeRepository.save(employee);
        //when
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        //then
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void givenEmail_whenFindByEmail_thenReturnEmployee(){
        //given
        employeeRepository.save(employee);
        //when
        Employee savedEmployee = employeeRepository.findByEmail(employee.getEmail()).get();
        //then
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void givenEmployeeId_whenUpdate_thenReturnUpdatedEmployee(){
        //given
        Employee savedEmployee = employeeRepository.save(employee);
        //when
        savedEmployee.setFirstName("Ravi");
        savedEmployee.setEmail("ravi123@gmail.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        //then
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ravi");
        assertThat(updatedEmployee.getEmail()).isEqualTo("ravi123@gmail.com");

    }

    @Test
    public void givenEmployee_whenDelete_thenRemoveEmployee(){
        //given
        Employee savedEmployee = employeeRepository.save(employee);
        Long employeeId = savedEmployee.getId();
        //when
        employeeRepository.deleteById(employeeId);
        Optional<Employee> deletedEmployee = employeeRepository.findById(employeeId);
        //then
        assertThat(deletedEmployee).isEmpty();

    }

    //Junit test for custom query using JPQL with index params
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployee(){
        //given
        employeeRepository.save(employee);
        String firstName = "Ravinder";
        String lastName = "Singh";
        //when
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);
        //then
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for custom query using JPQL with named params
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployee(){
        //given
        employeeRepository.save(employee);
        String firstName = "Ravinder";
        String lastName = "Singh";
        //when
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);
        //then
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for custom native query with index params
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployee(){
        //given
        employeeRepository.save(employee);
        //when
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(),
                employee.getLastName());
        //then
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for custom native query with named params
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamed_thenReturnEmployee(){
        //given
        employeeRepository.save(employee);
        //when
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());
        //then
        assertThat(savedEmployee).isNotNull();
    }
}