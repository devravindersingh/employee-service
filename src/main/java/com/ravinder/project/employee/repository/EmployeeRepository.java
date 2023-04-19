package com.ravinder.project.employee.repository;

import com.ravinder.project.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    //custom query using java persistence query language JPQL with index params
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);

    //custom query using JPQL with named params
    @Query("select e from Employee e where e.firstName =:firstNameParam and e.lastName =:lastNameParam")
    Employee findByJPQLNamedParams(@Param("firstNameParam") String firstName, @Param("lastNameParam") String lastName);

    //custom native query with index params
    @Query(
            value = "select * from employees e where e.first_name =?1 and e.last_name =?2",
            nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);

    //custom native query with named params
    @Query(
            value = "select * from employees e where e.first_name =:firstNameParam and e.last_name =:lastNameParam",
            nativeQuery = true)
    Employee findByNativeSQLNamed(@Param("firstNameParam") String firstName, @Param("lastNameParam") String lastName);


}
