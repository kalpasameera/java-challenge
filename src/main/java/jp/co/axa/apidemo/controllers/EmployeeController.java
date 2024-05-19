package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(employeeService.retrieveEmployees());
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        try {
            Employee employee = employeeService.getEmployee(employeeId);
            return ResponseEntity.ok(employee);
        } catch (EmployeeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(null);
        }
    }

    @PostMapping("/employees")
    public ResponseEntity<Void> saveEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EmployeeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(org.springframework.http.MediaType.APPLICATION_JSON).build();
        }
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<Void> updateEmployee(@RequestBody Employee employee, @PathVariable(name = "employeeId") Long employeeId) {
        try {
            employeeService.getEmployee(employeeId);
            employee.setId(employeeId);
            employeeService.updateEmployee(employee);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EmployeeNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(org.springframework.http.MediaType.APPLICATION_JSON).build();
        }
    }
}
