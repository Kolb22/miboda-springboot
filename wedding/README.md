# 💒 Mi Boda — Wedding RSVP Management API

A lightweight Spring Boot REST API for managing wedding RSVPs. Guests can look up their invitation by name, view their party list, and track RSVP status with expiration dates.

## Tech Stack

| Component       | Version / Details        |
|-----------------|--------------------------|
| Java            | 17                       |
| Spring Boot     | 3.0.5                    |
| Build Tool      | Maven                    |
| Database        | MySQL 8+                 |
| ORM             | Spring Data JPA / Hibernate |
| Utilities       | Lombok, ModelMapper 3.1.1 |

## Prerequisites

- **Java 17** or later
- **Maven 3.8+** (or use the included Maven Wrapper)
- **MySQL 8+** running on `localhost:3306`

## Getting Started

### 1. Create the MySQL database

```sql
CREATE DATABASE wedding;
```

### 2. Configure database credentials

Edit `src/main/resources/application.properties` and update the datasource settings to match your environment:

```properties
spring.datasource.url=jdbc:mysql://localhost/wedding?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=<your-password>
```

Hibernate is configured with `ddl-auto=update`, so tables are created/updated automatically on startup.

### 3. Build the project

```bash
./mvnw clean package
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

Or run the packaged JAR directly:

```bash
java -jar target/wedding-0.0.1-SNAPSHOT.jar
```

The API will be available at `http://localhost:8080/api`.

## API Endpoints

| Method | Endpoint            | Description                  | Request Body | Response          |
|--------|---------------------|------------------------------|--------------|-------------------|
| GET    | `/api/ok`           | Health check                 | —            | `200 OK`          |
| POST   | `/api/rsvp`         | Create a new RSVP invitation | `Rsvp` JSON  | Created `Rsvp`    |
| GET    | `/api/rsvp/{name}`  | Look up RSVP by guest name   | —            | `RsvpTO` JSON     |

### Create an RSVP

**POST** `/api/rsvp`

```json
{
  "name": "García Family",
  "email": "garcia@example.com",
  "expiredDate": "2026-12-31",
  "guests": [
    { "name": "Carlos García", "hasAccepted": false },
    { "name": "María García", "hasAccepted": false }
  ]
}
```

A 16-character invitation code is generated automatically.

### Look up an RSVP

**GET** `/api/rsvp/Carlos García`

Returns the RSVP details along with a `hasExpiredRsvp` flag indicating whether the deadline has passed.

## Data Model

### Rsvp

| Field        | Type           | Description                          |
|--------------|----------------|--------------------------------------|
| id           | Long           | Auto-generated primary key           |
| code         | String (16)    | Auto-generated unique invitation code|
| name         | String         | Invitation holder name               |
| email        | String         | Unique email address                 |
| expiredDate  | Date           | RSVP deadline                        |
| guests       | List\<Guest\>  | Guests associated with this RSVP     |

### Guest

| Field        | Type    | Description                         |
|--------------|---------|-------------------------------------|
| id           | Long    | Auto-generated primary key          |
| name         | String  | Guest's full name                   |
| hasAccepted  | boolean | Whether the guest has accepted      |
| acceptedDate | Date    | Date of acceptance                  |

**Relationship:** Each `Rsvp` has a one-to-many relationship with `Guest` (cascade delete, orphan removal).

## Project Structure

```
wedding/
├── pom.xml
├── mvnw / mvnw.cmd
└── src/
    ├── main/
    │   ├── java/com/example/wedding/
    │   │   ├── WeddingApplication.java          # Entry point & config
    │   │   ├── Controllers/
    │   │   │   └── RsvpController.java          # REST endpoints
    │   │   ├── models/
    │   │   │   ├── Rsvp.java                    # RSVP entity
    │   │   │   ├── Guest.java                   # Guest entity
    │   │   │   └── dto/
    │   │   │       └── RsvpTO.java              # Response DTO
    │   │   ├── services/
    │   │   │   ├── IRsvpService.java            # Service interface
    │   │   │   └── impl/
    │   │   │       └── RsvpServiceImpl.java     # Service implementation
    │   │   └── dao/
    │   │       ├── RsvpDao.java                 # RSVP repository
    │   │       └── GuestDao.java                # Guest repository
    │   └── resources/
    │       └── application.properties           # App configuration
    └── test/
        └── java/com/example/wedding/
            └── WeddingApplicationTests.java     # Integration test
```

## Running Tests

```bash
./mvnw test
```