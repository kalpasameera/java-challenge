package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Cacheable(value = "employees")
    @Override
    public List<Employee> retrieveEmployees() {
        return employeeRepository.findAll();
    }

    @Cacheable(value = "employee", key = "#employeeId")
    @Override
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        if (optEmp.isPresent()) {
            return optEmp.get();
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + employeeId);
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "employees", allEntries = true),
            @CacheEvict(value = "employee", key = "#employee.id")
    })
    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Caching(evict = {
            @CacheEvict(value = "employees", allEntries = true),
            @CacheEvict(value = "employee", key = "#employeeId")
    })
    @Override
    public void deleteEmployee(Long employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + employeeId);
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "employees", allEntries = true),
            @CacheEvict(value = "employee", key = "#employee.id")
    })
    @Override
    public void updateEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            employeeRepository.save(employee);
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + employee.getId());
        }
    }
}
