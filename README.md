# TeacherBot Backend

TeacherBot backend is the REST API for the teacherbot project.

## Built with

-  Kotlin
-  Spring Boot
-  Bean validation
-  Spring Data JPA
-  PostgreSQL (dev, prod), H2 Database (test)
-  Kotest

## Getting Started

### Prerequisites
-  Docker
-  Docker Compose

### Installation and Running Local

1.  Clone the repo
```bash
git clone https://github.com/domingoslatorre/teacherbot-backend.git
```

2.  Run Gradle tests
```bash
./gradlew test
```

3.  Run Gradle Build
```bash
./gradlew build
```

4.  Run Docker Compose
```bash
docker-compose up --build
```

### Usage
-  Spring Rest API: http://localhost:8080
-  pgAdmin: http://localhost:16543
    -  email: admin@email.com
    -  password: 123456
-  Postman Collection: 
  