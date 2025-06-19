# 🐾 PamiętamPsa – Pet Care Tracker API

**PamiętamPsa** or **PamPsa** shortened (Polish for *"I remember the dog"*) is a backend REST API built with **Spring Boot** that allows users to manage their pets, receive notifications, and track key pet care activities. Designed with simplicity and real-life usability in mind, this project supports modern authentication and a growing feature set.

---

## 🐶 Features

- **User Authentication** (JWT-based login & registration)
- **Pet Management** – Add, view, edit, and delete pet profiles
- **Reminders System** *(in progress)* – Schedule and manage care-related reminders
- **Email Notifications** – Triggered on user registration, pet addition, etc.
- **Pet Health Records** *(planned)* – Log health events and medical history
- **Production-ready stack** with PostgreSQL and security best practices

---

## 🛠️ Tech Stack

| Layer       | Technology         |
|-------------|--------------------|
| Backend     | Spring Boot        |
| Database    | PostgreSQL         |
| Auth        | JWT (stateless)    |
| Mail        | SMTP (email alerts)|
| Deployment  | Railway.app        |

---

## 📦 API Overview

The API is versioned and documented with Swagger UI, which provides a visual interface to explore and test endpoints.
> 🔐 Access to Swagger is restricted to users with the ADMIN role.


Frontend URL: [pamietampsa.app](https://pamietampsa.netlify.app/)

Base API URL: api.pam*****.app/v1

Docs URL: /swagger-ui.html



### Example Endpoints
| Method | Endpoint           | Description                   |
|--------|--------------------|-------------------------------|
| POST   | `/v1/auth/signup`  | Register a new user           |
| POST   | `/v1/auth/login`   | Authenticate user, get token  |
| GET    | `/v1/pets/all`     | Get current user's pets       |
| POST   | `/v1/pets/add`     | Add a new pet                 |
| PUT    | `/v1/pets/{id}`    | Update existing pet           |
| DELETE | `/v1/pets/{id}`    | Delete pet                    |

---

## 🔐 Authentication

All protected endpoints require a **Bearer JWT token** in the `Authorization` header:

Authorization: Bearer < user-jwt-token>

---

## 📧 Email Notifications

Email events currently include:
- Successful user registration
- New pet added

Planned:
- Reminders for pet care
- Health record logs and alerts

---

## 🚧 Work in Progress
This is an actively developed backend. Coming soon:

- Complete Reminders CRUD + Notification Triggers

- OAuth2.0 (Google & Facebook)

- Pet Health Record Management

- Rate limiting, role-based access control (RBAC), and other security hardening


### 🧑‍💻 Contributing
Kindly contact me for the possibility of collaborating/contributing, I look forward to hearing from you!

---

Developed by **[heisdanielade](https://www.heisdanielade.xyz/)**

---
