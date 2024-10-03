package com.employeemanagement.repository;

import com.employeemanagement.model.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testSaveAndFindEmployee() {
        Employee employee = Employee.builder().firstName("John").lastName("Doe").email("john.doe@example.com").department("Engineering").salary(75000.0).build();

        employeeRepository.save(employee);


        Employee foundEmployee = employeeRepository.findByEmail("john.doe@example.com").orElse(null);

        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getFirstName()).isEqualTo("John");
        assertThat(foundEmployee.getLastName()).isEqualTo("Doe");
        assertThat(foundEmployee.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(foundEmployee.getDepartment()).isEqualTo("Engineering");
        assertThat(foundEmployee.getSalary()).isEqualTo(75000.0);
    }

    @Test
    void testEmployeeEmailUniqueness() {
        Employee employee1 = Employee.builder().firstName("Jane").lastName("Doe").email("jane.doe@example.com").department("Marketing").salary(65000.0).build();

        Employee employee2 = Employee.builder().firstName("Jack").lastName("Smith").email("jane.doe@example.com").department("Sales").salary(70000.0).build();

        Employee employee= employeeRepository.save(employee1);
        assertNotNull(employee.getId());
        assertThrows(DataIntegrityViolationException.class, () -> {
            employeeRepository.save(employee2);
        });
    }
}
