# Employee Leave Management System

Spring Boot backend application for managing employee leave requests with **JWT authentication** and **role-based access control (Employee, Manager, HR)**.

Detailed architecture explanation, API testing screenshots, and backend workflow are available in the documentation folder below.

---

## Tech Stack

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* H2 Database
* Maven
* Postman

---

## Default Credentials

| Role     | Username | Password |
| -------- | -------- | -------- |
| Employee | employee | password |
| Manager  | manager  | password |
| HR       | hr       | password |

---

## Important APIs

Login
`POST /auth/login`

Apply Leave
`POST /leave/apply`

View My Leaves
`GET /leave/my`

Approve / Reject Leave
`PUT /leave/{id}/status`

HR Reports
`GET /reports/all`

---

## Running the Project

Clone the repository

```
git clone https://github.com/Yuvraj599/EmployeeManagement.git
```

Run the application

```
mvn spring-boot:run
```

Application runs at

```
http://localhost:8083
```

---

## H2 Database Console

URL

```
http://localhost:8083/h2-console
```

JDBC URL

```
jdbc:h2:mem:leavedb
```

Username

```
sa
```

Password

```
(blank)
```

---

## Documentation & Screenshots

Architecture explanation, API testing screenshots, and backend workflow:

**Google Drive:**
https://drive.google.com/drive/folders/1nDStERqOQqLc3zMzYmlD4r4NQtv9WwQd?usp=sharing

---

## Author

Yuvraj Sanap
Computer Engineering Student
