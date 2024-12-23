# Gym Web Application API 🏋️‍♂️

A comprehensive gym management system built with Spring Boot that provides a robust platform for managing gym operations, including member management, class scheduling, attendance tracking, and user authentication. The application utilizes Spring Security with JWT for secure authentication and PostgreSQL for reliable data storage.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Setup](#setup)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
  - [Authentication](#authentication)
  - [Members](#members)
  - [Classes](#classes)
  - [Class Schedules](#class-schedules)
  - [Attendance](#attendance)
- [Contributing](#contributing)
- [License](#license)

## Features

- User authentication and authorization with JWT
- Member management system
- Class scheduling and management
- Attendance tracking
- Role-based access control (Admin, Member, Trainer)
- User profile management

## Technologies
Make sure you have these installed and setup before running the application

- Java 17+
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit & Mockito

## Setup

1. **Clone the repository:**

    ```bash
    git clone https://github.com/yourusername/gym-web-app.git
    cd gym-web-app
    ```

2. **Configure the database:**

    Update `src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/gym_db
    spring.datasource.username=your_postgres_username
    spring.datasource.password=your_postgres_password
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    jwt.secret=your_jwt_secret_key
    jwt.expiration=86400000
    ```

3. **Initialize the database:**

    ```sql
    -- Create roles
    INSERT INTO role (name) VALUES ('MEMBER');
    INSERT INTO role (name) VALUES ('ADMIN');
    INSERT INTO role (name) VALUES ('TRAINER');

    -- Create admin user
    INSERT INTO Users (username, email, password)
    VALUES ('admin', 'admin@gym.com', '$2a$10$yourhashedpassword');

    -- Assign admin role
    INSERT INTO User_Role (user_id, role_id)
    SELECT u.id, r.id
    FROM Users u, Role r
    WHERE u.username = 'admin' AND r.name = 'ADMIN';
    ```

4. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```
