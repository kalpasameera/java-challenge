package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * Retrieve all employees.
     * @return List of employees
     */
    List<Employee> retrieveEmployees();

    /**
     * Get an employee by ID.
     * @param employeeId the ID of the employee
     * @return the employee, or null if not found
     */
    Employee getEmployee(Long employeeId);

    /**
     * Save a new employee.
     * @param employee the employee to save
     */
    void saveEmployee(Employee employee);

    /**
     * Delete an employee by ID.
     * @param employeeId the ID of the employee to delete
     */
    void deleteEmployee(Long employeeId);

    /**
     * Update an existing employee.
     * @param employee the employee with updated details
     */
    void updateEmployee(Employee employee);
}
