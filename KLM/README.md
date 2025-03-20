# FastTrack Holidays Assignment Backend

## Overview

This repository contains the backend for the FastTrack Holidays Assignment. The backend is built using Java with Spring Boot and provides a RESTful API to manage crew holidays. The system allows users to view, create, update, and delete holiday records while adhering to the business rules defined for holiday scheduling.

### Business Rules
- There must be a gap of at least 3 working days between holidays.
- Holidays must be planned at least 5 working days in advance.
- Holidays must be canceled at least 5 working days in advance.
- Holidays should not overlap, even between different crew members.

## Features

- **Holiday Management**: Crew members can view, create, delete, and update holiday records.
- **Holiday Validation**: The system checks for holiday overlap, the required lead time for holidays, and ensures there is a gap of at least 3 working days between holidays.
- **Employee Support**: Crew members can view their own holidays and schedule new ones.

## Endpoints

### 1. Get All Holidays

- **URL**: `/holidays`
- **Method**: `GET`
- **Description**: Fetches all holidays.
- **Response**:
  ```json
  [
    {
      "holidayId": "uuid",
      "holidayLabel": "Summer Holidays",
      "employeeId": "klm012345",
      "startOfHoliday": "2022-08-02T08:00:00+00:00",
      "endOfHoliday": "2022-08-16T08:00:00+00:00",
      "status": "SCHEDULED"
    }
  ]

### 2. Get Holiday by ID

- **URL**: `/holidays/{holidayId}`
- **Method**: `GET`
- **Description**: Fetches a holiday by its ID.
- **Response**:
  ```json
  {
  "holidayId": "uuid",
  "holidayLabel": "Summer Holidays",
  "employeeId": "klm012345",
  "startOfHoliday": "2022-08-02T08:00:00+00:00",
  "endOfHoliday": "2022-08-16T08:00:00+00:00",
  "status": "SCHEDULED"
  }

### 3. Get Holidays by Employee

- **URL**: `/holidays/employee/{employeeId}`
- **Method**: `GET`
- **Description**: Fetches all holidays for a specific employee.
- **Response**:
  ```json
  [
  {
    "holidayId": "uuid",
    "holidayLabel": "Summer Holidays",
    "employeeId": "klm012345",
    "startOfHoliday": "2022-08-02T08:00:00+00:00",
    "endOfHoliday": "2022-08-16T08:00:00+00:00",
    "status": "SCHEDULED"
  }
  ]

### 4. Create a Holiday

- **URL**: `/holidays`
- **Method**: `POST`
- **Description**: Creates a new holiday.
- **Request**:
```json
{
  "holidayLabel": "Summer Holidays",
  "employeeId": "klm012345",
  "startOfHoliday": "2022-08-02T08:00:00+00:00",
  "endOfHoliday": "2022-08-16T08:00:00+00:00",
  "status": "REQUESTED"
}

```

- **Response**:
  ```json
  {
  "holidayId": "uuid",
  "holidayLabel": "Summer Holidays",
  "employeeId": "klm012345",
  "startOfHoliday": "2022-08-02T08:00:00+00:00",
  "endOfHoliday": "2022-08-16T08:00:00+00:00",
  "status": "SCHEDULED"
  }

### 5. Delete a Holiday

- **URL**: `/holidays/{holidayId}`
- **Method**: `DELETE`
- **Description**: Deletes a holiday by ID.
- **Response**: 204 No Content if successful.

### 6. Update a Holiday

- **URL**: `/holidays/{holidayId}`
- **Method**: `PUT`
- **Description**: Updates an existing holiday.
- **Request**:
```json
{
  "holidayLabel": "Summer Holidays",
  "employeeId": "klm012345",
  "startOfHoliday": "2022-08-02T08:00:00+00:00",
  "endOfHoliday": "2022-08-16T08:00:00+00:00",
  "status": "REQUESTED"
}

```

- **Response**:
  ```json
  {
  "holidayId": "uuid",
  "holidayLabel": "Summer Holidays",
  "employeeId": "klm012345",
  "startOfHoliday": "2022-08-02T08:00:00+00:00",
  "endOfHoliday": "2022-08-16T08:00:00+00:00",
  "status": "SCHEDULED"
  }

### Installation

1. Navigate to the project folder and build the project using Maven:

   ```bash
   mvn clean install
    ```
2. Run the application:

   ```bash
   mvn spring-boot:run
    ```
   The application will run on http://localhost:8080.

## Technologies Used

- **Java 17**
- **Spring Boot 3.x** (for REST APIs)
- **Spring Data JPA** (for database interaction)
- **H2 Database** (in-memory database for testing)
- **SLF4J** (for logging)
- **Lombok** (for simplifying POJOs)
- **Spring Boot Starter Web** (for building web applications)
- **Spring Boot Starter Test** (for testing)

## Configuration

The project uses an in-memory H2 database by default. You can change the configuration in application.properties to connect to a different database if needed.
```bash
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:test
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update


