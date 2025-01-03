# E-Commerce 

This is a Spring Boot project designed to manage a REST API and handle e-commerce transactions. It leverages the power of Spring Boot to create an efficient, scalable, and easy-to-maintain backend system.

## Features

- RESTful APIs: Exposes endpoints for user management, order processing, invoice management, product management, etc.
- Database Integration: Uses JPA Hibernate with a MySQL database for persistence.
- Pagination & Sorting: Includes pagination and sorting for large sets of data using Spring Data JPA repositories.
- Scheduled Tasks: Runs background tasks periodically using Spring's @Scheduled annotation for processes like clean-up or expired carts removal.
- Async Processing: Asynchronous task execution for long-running operations to improve app responsiveness and scalability.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- JDK 11+ (or compatible version)
- Maven or Gradle (for building the project)
- Database: MySQL

  
## Configuration

Make sure your MySQL database is running and accessible.

Modify the application.properties file to configure your database connection. 
- spring.datasource.url=jdbc:mysql://localhost:yourport/yourdb
- spring.datasource.username=root
- spring.datasource.password=yourpassword
- spring.jpa.hibernate.ddl-auto=update
- spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

for MySQL Workbench 8.0 CE the default port is 3306
