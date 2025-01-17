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
  "id": 0,
  "username": "string",
  "email": "string",
  "password": "string",
  "roleIds": [
    0
  ]
}
```
- **POST /api/auth/login**: Login and get JWT token
 ```json
 {
   "username": "string",
   "password": "string"
 }
```

### Users

- **POST /api/users**: new user
 ```json
{
  "id": 0,
  "username": "string",
  "email": "string",
  "password": "string",
  "roleIds": [
    0
  ]
}
```
- **GET /api/users/**: Get all users
- **GET /api/users/{id}**: Get user by ID
- **DELETE /api/users/{id}**: DELETE user by ID
- **PUT /api/users/{id}**: Update user data
 ```json
{
  "id": 0,
  "username": "string",
  "email": "string",
  "password": "string",
  "roleIds": [
    0
  ]
}
```

### Roles

- **POST /api/roles**: new user
 ```json
{
  "id": 0,
  "name": "string"
}
```
- **GET /api/roles/**: Get all roles
- **GET /api/roles/{id}**: Get role by ID
- **DELETE /api/roles/{id}**: DELETE role by ID
- **PUT /api/roles/{id}**: Update role data
 ```json
{
  "id": 0,
  "name": "string"
}
```

### Members

- **GET /api/members**: Get all members
- **GET /api/members/{id}**: Get member by ID
- **POST /api/members**: Create new member
```json
{
  "id": 0,
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "dateOfBirth": "YYYY-MM-DD",
  "joinDate": "YYYY-MM-DD",
  "membershipType": "string",
  "active": true,
  "classScheduleIds": [
    0
  ],
  "userId": 0
}
```
- **PUT /api/members/{id}**: Update member
```json
{
"id": 0,
"firstName": "string",
"lastName": "string",
"email": "string",
"dateOfBirth": "YYYY-MM-DD",
"joinDate": "YYYY-MM-DD",
"membershipType": "string",
"active": true,
"classScheduleIds": [
  0
],
"userId": 0
}
```
- **DELETE /api/members/{id}**: Delete member by ID

#### Classes
- **GET /api/classes**: Get all classes
- **GET /api/classes/{id}**: Get class by ID
- **POST /api/classes**: Create new class
```json
{
  "id": 0,
  "title": "string",
  "duration": 0,
  "maxParticipants": 0,
  "instructorId": 0,
  "scheduleIds": [
    0
  ]
}
```
- **PUT /api/classes/{id}**: Update class
```json
{
  "id": 0,
  "title": "string",
  "duration": 0,
  "maxParticipants": 0,
  "instructorId": 0,
  "scheduleIds": [
    0
  ]
}
```
- **DELETE /api/classes/{id}**: Delete class by ID

### Class Schedules

- **GET /api/class-schedules**: Get all schedules
- **GET /api/class-schedules/{id}**: Get schedule  by ID
- **POST /api/class-schedules**: Create schedule
```json
{
  "id": 0,
  "classId": 0,
  "date": "YYYY-MM-DD",
  "startTime": "HH:MM:SS",
  "endTime": "HH:MM:SS"
}
```
- **PUT /api/class-schedules/{id}**: Update schedule by ID
```json
{
  "id": 0,
  "classId": 0,
  "date": "YYYY-MM-DD",
  "startTime": "HH:MM:SS",
  "endTime": "HH:MM:SS"
}
```
- **DELETE /api/class-schedules/{id}**: DELETE schedule by ID

### Attendance

- **GET /api/attendance**: Get all attendance records
- **GET /api/attendance/{id}**: Get attendance record by ID
- **POST /api/attendance**: Mark attendance
```json
{
  "id": 0,
  "memberId": 0,
  "classScheduleId": 0,
  "attendanceTime": "YYYY-MM-DDTHH:MM:SS.NNNZ",
  "attended": true
}
```
- **PUT /api/attendance/{id}**: Update attendance by ID
```json
{
  "id": 0,
  "memberId": 0,
  "classScheduleId": 0,
  "attendanceTime": "YYYY-MM-DDTHH:MM:SS.NNNZ",
  "attended": true
}
```

### Sign Up

- **GET /api/signups**: Get all signups records
- **GET /api/signups/{id}**: Get signup record by ID
- **POST /api/signups**: Create signup
```json
{
"id": 0,
"memberId": 0,
"classScheduleId": 0,
"signupTime": "YYYY-MM-DDTHH:MM:SS.NNNZ"
}
```
- **PUT /api/signups/{id}**: Update signup by ID
```json
{
"id": 0,
"memberId": 0,
"classScheduleId": 0,
"signupTime": "YYYY-MM-DDTHH:MM:SS.NNNZ"
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
This project is licensed under the MIT License.
