package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveEmployees() {
        Employee employee = new Employee();
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

        assertEquals(1, employeeService.retrieveEmployees().size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void testGetEmployee() {
        Employee employee = new Employee();
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        assertEquals(employee, employeeService.getEmployee(1L));
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployee(1L);
        });
    }

    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee();
        employeeService.saveEmployee(employee);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testDeleteEmployee() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteEmployeeNotFound() {
        when(employeeRepository.existsById(1L)).thenReturn(false);

        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.deleteEmployee(1L);
        });
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        when(employeeRepository.existsById(1L)).thenReturn(true);
        employeeService.updateEmployee(employee);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testUpdateEmployeeNotFound() {
        Employee employee = new Employee();
        employee.setId(1L);
        when(employeeRepository.existsById(1L)).thenReturn(false);

        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.updateEmployee(employee);
        });
    }
}
