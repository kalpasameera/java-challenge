# Java Challenge

## Project Overview
This project is a Spring Boot application that manages employee data with basic CRUD operations. It uses an embedded H2 database and includes Swagger for API documentation.

### Features:
- Create, Read, Update, Delete (CRUD) operations for employees.
- Exception handling for not found employees.
- Security configurations using Spring Security.
- Caching to improve performance.
- Aspect-Oriented Programming (AOP) for cross-cutting concerns.
- Unit tests for service and controller layers.

## Prerequisites
- Java 8
- Maven

## Setup Instructions
1. **Clone the Repository**
    ```bash
    git clone https://github.com/yourusername/java-challenge.git
    cd java-challenge
    ```

2. **Install Dependencies**
    ```bash
    mvn package
    ```

3. **Run the Application**
    ```bash
    mvn spring-boot:run
    ```
    or use your IDE to run the `ApiDemoApplication` class.

## Accessing the Application
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - JDBC URL: `jdbc:h2:mem:testdb`


## Enhancements
### Code Enhancements:
1. **Security**:
    - Added basic authentication using Spring Security.
    - Secured endpoints to require authentication.

2. **Caching**:
    - Implemented caching for employee data to improve performance.

3. **Exception Handling**:
    - Added global exception handling for employee not found scenarios.

4. **Aspect-Oriented Programming (AOP)**:
    - Used AOP to add additional behavior after service method executions.

### Unit Tests:
- Added unit tests for the controller and service layers using JUnit and Mockito.

## Future Improvements
- **Logging**: Enhance logging for better traceability and debugging.

## Experience in Java
I'm a beginner and just recently learned Spring Boot
