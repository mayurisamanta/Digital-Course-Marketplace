<a name="readme-top"></a>

# Digital Course Marketplace - Backend Application

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Used](#technology-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [License](#license)
- [Contact](#contact)

---

## Overview

**Digital Course Marketplace - Backend Application** is the backend API service for a digital marketplace that allows users to browse, purchase, and manage courses. This backend application, built using **Kotlin** and **OpenJDK v21**, offers RESTful endpoints for handling courses, user accounts, payments, and analytics.

The backend ensures scalability, security, and seamless integration with the frontend for a smooth user experience.

---

## Features

- **User Authentication**: Secure sign-up, login, and JWT-based authentication to manage user sessions and protect sensitive endpoints.
  
- **Course Management**: 
  - **Course Creators** can easily add new courses with details like title, description, and pricing.
  - **View Created Courses**: Creators can view and manage all the courses they have added.

- **Admin Panel**: 
  - **Admins** have full access to manage user accounts

- **Customer Functionality**: 
  - **Customers** can browse all available courses, view course details, and make purchases securely.


---

## Technology Used

- **Kotlin**: Programming language for backend development.
- **OpenJDK 21**: Java runtime environment.
- **Spring Boot**: Framework for building the application.
- **Maven**: Build tool for dependency management and packaging.
- **JWT (JSON Web Tokens)**: For secure authentication.
- **H2 Database**: In-memory relational database for storing course and user data during development.
- **Docker**: For containerizing the application.

---

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java 21** (or higher) installed on your system.
- **Maven** installed for building the project.
- **H2 Database**: The project uses an in-memory H2 database by default, so no external database setup is required during development.
- **Docker**: If you want to run the application in a Docker container.

## Installation

### Simple Installation

Run the following command to start the application in a Docker container: 

```
docker run --name=digital-course-marketplace -p 8080:8080 -d mayurisamanta/digital-course-marketplace:latest
```
### Installation without Docker

1. Clone this repository:

   ```bash
   git clone https://github.com/mayurisamanta/Digital-Course-Marketplace.git
2. Navigate to the project directory:

    ```bash
   cd digital-course
3. Build the project:

    ```bash
   mvn clean install
4. Start the application:

    ```bash
    mvn spring-boot:run

### Installation with Docker

1. Clone this repository:

   ```bash
   git clone https://github.com/mayurisamanta/Digital-Course-Marketplace.git
2. Navigate to the project directory:

    ```bash
   cd digital-course
3. Run the docker compose command:

    ```bash
    docker compose up -d

### Accessing the Application
The application will be accessible at http://localhost:8080.   

---


## License

This project is licensed under the **MIT License**. You can find the details in the [LICENSE](https://github.com/mayurisamanta/DigitalCourseMarketplace-backend/blob/master/LICENSE) file.

---


### Contact

If you have any questions or suggestions, feel free to reach out via one of the platforms linked below:

- **Portfolio**: [mayurisamanta.me](https://mayurisamanta.me)
- **GitHub**: [Mayuri Samanta](https://github.com/mayurisamanta)
- **LinkedIn**: [Mayuri Samanta](https://linkedin.com/in/mayuri-samanta)
- **Email**: [dev.mayurisamanta@gmail.com](mailto:dev.mayurisamanta@gmail.com)

---

Happy coding!

<p align="right">(<a href="#readme-top">back to top</a>)</p>

