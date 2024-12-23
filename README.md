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
## Usage
Access the application at http://localhost:8080. Use Postman or Swagger UI (http://localhost:8080/swagger-ui.html) to test the endpoints. After registration and login, use the JWT token in the Authorization header for subsequent requests.

## API Endpoints

### Authentication

- **POST /api/auth/register**: Register a new user
 ```json
 {
   "username": "string",
   "password": "string",
   "email": "string"
 }
- **POST /api/auth/login**: Login and get JWT token
 ```json
 {
   "username": "string",
   "password": "string"
 }

### Members

- **GET /api/members**: Get all members
- **GET /api/members/{id}**: Get member by ID
- **POST /api/members**: Create new member
```json
{
   "firstName": "string",
   "lastName": "string", 
   "email": "string",
   "dateOfBirth": "date",
   "membershipType": "string"
}
- **PUT /api/members/{id}**: Update member
- **DELETE /api/members/{id}**: Delete member

#### Classes
- **GET /api/classes**: Get all classes
- **GET /api/classes/{id}**: Get class by ID
- **POST /api/classes**: Create new class
```json
{
   "title": "string",
   "duration": "integer",
   "maxParticipants": "integer", 
   "instructorId": "long"
}
- **PUT /api/classes/{id}**: Update class
- **DELETE /api/classes/{id}**: Delete class

### Class Schedules

- **GET /api/class-schedules**: Get all schedules
- **POST /api/class-schedules**: Create schedule
```json
{
   "classId": "long",
   "date": "date",
   "startTime": "time",
   "endTime": "time"
}

### Attendance

- **GET /api/attendance**: Get all attendance records
- **POST /api/attendance**: Mark attendance
```json
{
   "memberId": "long",
   "classScheduleId": "long", 
   "attended": "boolean"
}
```
## Contributing

1. Fork the project
2. Create feature branch
   ```bash
   git checkout -b feature/NewFeature
   ```
3. Write clean code and tests
4. Commit changes
   ```bash 
   git commit -m "feat: add new feature"
   ```
5. Push to branch
   ```bash
   git push origin feature/NewFeature
   ```
6. Open a Pull Request

Code must include tests, documentation, and follow REST standards. Two approvals required.

Questions? Open an issue! 🚀

## License
This project is licensed under the MIT License
