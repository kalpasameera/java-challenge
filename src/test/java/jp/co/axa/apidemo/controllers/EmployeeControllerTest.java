package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testGetEmployees() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setSalary(1000);
        employee.setDepartment("IT");

        when(employeeService.retrieveEmployees()).thenReturn(Arrays.asList(employee));

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].salary").value(1000))
                .andExpect(jsonPath("$[0].department").value("IT"));

        verify(employeeService, times(1)).retrieveEmployees();
    }

    @Test
    public void testGetEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setSalary(1000);
        employee.setDepartment("IT");

        when(employeeService.getEmployee(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.salary").value(1000))
                .andExpect(jsonPath("$.department").value("IT"));

        verify(employeeService, times(1)).getEmployee(1L);
    }

    @Test
    public void testGetEmployeeNotFound() throws Exception {
        when(employeeService.getEmployee(1L)).thenThrow(new EmployeeNotFoundException("Employee not found with id: 1"));

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(employeeService, times(1)).getEmployee(1L);
    }

    @Test
    public void testSaveEmployee() throws Exception {
        doNothing().when(employeeService).saveEmployee(any(Employee.class));

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"salary\":1000,\"department\":\"IT\"}"))
                .andExpect(status().isCreated());

        verify(employeeService, times(1)).saveEmployee(any(Employee.class));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    public void testDeleteEmployeeNotFound() throws Exception {
        doThrow(new EmployeeNotFoundException("Employee not found with id: 1")).when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setSalary(1000);
        employee.setDepartment("IT");

        when(employeeService.getEmployee(1L)).thenReturn(employee);
        doNothing().when(employeeService).updateEmployee(any(Employee.class));

        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"salary\":1000,\"department\":\"IT\"}"))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).getEmployee(1L);
        verify(employeeService, times(1)).updateEmployee(any(Employee.class));
    }

    @Test
    public void testUpdateEmployeeNotFound() throws Exception {
        when(employeeService.getEmployee(1L)).thenThrow(new EmployeeNotFoundException("Employee not found with id: 1"));

        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"salary\":1000,\"department\":\"IT\"}"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).getEmployee(1L);
    }
}
