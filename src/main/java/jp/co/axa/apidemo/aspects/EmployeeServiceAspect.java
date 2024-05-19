package jp.co.axa.apidemo.aspects;

import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.services.EmployeeService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmployeeServiceAspect {

    @AfterReturning(pointcut = "execution(* jp.co.axa.apidemo.services.EmployeeService.getEmployee(..))", returning = "result")
    public void afterGetEmployee(Object result) {
        if (result == null) {
            throw new EmployeeNotFoundException("Employee not found with the given id");
        }
    }

    @AfterReturning(pointcut = "execution(* jp.co.axa.apidemo.services.EmployeeService.deleteEmployee(..))", returning = "result")
    public void afterDeleteEmployee(Object result) {
        // Implement the logic to throw an exception if the employee is not found
        // This method is invoked after the deleteEmployee method returns
    }

    @AfterReturning(pointcut = "execution(* jp.co.axa.apidemo.services.EmployeeService.updateEmployee(..))", returning = "result")
    public void afterUpdateEmployee(Object result) {
        // Implement the logic to throw an exception if the employee is not found
        // This method is invoked after the updateEmployee method returns
    }
}
