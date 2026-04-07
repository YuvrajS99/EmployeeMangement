# Employee Leave Management System

Spring Boot backend application for managing employee leave requests with **JWT authentication** and **role-based access control (Employee, Manager, HR)**.

Detailed architecture and workflow are explained in the **attached PDF document**.

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

## Run the Project

Clone repository

```
git clone https://github.com/Yuvraj599/EmployeeManagement.git
```

Run application

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

# API Testing Screenshots

## Project Structure

![Project Structure](Screenshots/Screenshot%20\(2060\).png)

## Spring Boot Configuration

![Configuration](Screenshots/Screenshot%20\(2061\).png)

## Controller Implementation

![Controller](Screenshots/Screenshot%20\(2065\).png)

## Service Layer

![Service](Screenshots/Screenshot%20\(2066\).png)

## H2 Database Tables

![Database Tables](Screenshots/Screenshot%20\(2067\).png)

## Users Table

![Users Table](Screenshots/Screenshot%20\(2068\).png)

## Login API (JWT Token)

![Login API](Screenshots/Screenshot%20\(2069\).png)

## Manager Login

![Manager Login](Screenshots/Screenshot%20\(2070\).png)

## Employee Leave Requests

![Leave Requests](Screenshots/Screenshot%20\(2071\).png)

## Leave Status in Database

![Leave Status](Screenshots/Screenshot%20\(2072\).png)

## HR Reports API
![Uploading Screenshot (2073).png…]()


## Leave Summary Output

![Leave Summary](Screenshots/Screenshot%20\(2074\).png)

---

## Author

Yuvraj Sanap
Computer Engineering Student
