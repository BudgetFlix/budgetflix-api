# BudgetFlix API

> Backend API for BudgetFlix, connecting the frontend, media metadata, upload jobs, and the streaming pipeline.

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)

---

## Overview

BudgetFlix API is the backend layer between the frontend, the remote media data environment, RabbitMQ, and the video processing worker.

It is responsible for:

- serving movie metadata
- accepting media upload jobs
- storing basic processing state
- sending video processing messages to RabbitMQ
- returning HLS stream entry paths to the client

The API does not process videos and does not directly serve video files. Video processing belongs to a separate media worker, while generated HLS files can be served by a static server such as Nginx.

---

## Tech Stack

| Area | Technology |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 3.1.0 |
| API | Spring Web REST |
| Persistence | Spring Data JPA, Hibernate |
| Message broker | RabbitMQ |
| Mapper | MapStruct |
| Boilerplate reduction | Lombok |
| Build | Maven Wrapper |
| Container | Docker, Eclipse Temurin JRE |
| CI/CD | GitHub Actions, GHCR image push |
| Runtime data | Remote machine / external environment |

> Note: the repository contains SQLite configuration and a local `budgetflix.db` for development or local testing. The real data environment is remote, so SQLite should not be treated as the production source of truth.

---

## Project Structure

```text
src/main/java/hu/budgetflix/api
|-- config          # RabbitMQ configuration
|-- controller      # REST endpoints
|-- exception       # Central exception handling
|-- mapper          # Entity to DTO mapping
|-- model           # Entities, DTOs, enums
|-- producer        # RabbitMQ producer
|-- repository      # Spring Data repositories
`-- service         # Business logic
```

---

## Endpoints

### Get All Movies

```http
GET /api/movies
```

Response:

```json
[
  {
    "title": "Example Movie",
    "id": 1
  }
]
```

### Get Stream Path

```http
GET /api/stream/{id}
```

Response:

```text
/stream/movies/{id}/hls/index.m3u8
```

Example:

```text
/stream/movies/1/hls/index.m3u8
```

### Start Media Upload Job

```http
PUT /api/upload
Content-Type: application/json
```

Request:

```json
{
  "jobID": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Example Movie",
  "type": "MOVIE",
  "videos": {
    "1080": "/uploads/example-1080.mp4",
    "720": "/uploads/example-720.mp4"
  }
}
```

Response:

```text
Message sent!
```

This endpoint creates a `movie` record with `PROCESS` status, then sends a processing message to the RabbitMQ `video.upload.queue` queue.

---

## Data Model

### Movie

| Field | Type | Description |
| --- | --- | --- |
| `id` | `Long` | Unique identifier |
| `title` | `String` | Movie title |
| `status` | `String` | Processing status |
| `createdAt` | `LocalDateTime` | Creation timestamp |
| `hls_path` | `String` | HLS output path |

### Status Values

```text
DONE
ERROR
PROCESS
```

### Media Types

```text
MOVIE
SERIES
```

---

## RabbitMQ Flow

```text
Client -> API -> Remote data environment
              -> RabbitMQ queue -> media-worker -> HLS output
```

1. The client sends upload metadata to `/api/upload`.
2. The API creates a movie record with `PROCESS` status.
3. The API sends a `MediaMessage` to `video.upload.queue`.
4. The media worker processes the videos and generates HLS output.
5. The client can later request the stream entry path from `/api/stream/{id}`.

---

## Configuration

The current `prod` profile contains a JDBC configuration driven by `DB_PATH`:

```properties
spring.datasource.url=jdbc:sqlite:${DB_PATH}
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

This is useful for local development and simple runtime wiring. In the real deployment, data lives on a remote machine / external environment, so configure the runtime according to that host.

RabbitMQ can be configured with standard Spring Boot AMQP properties, for example:

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

---

## Running Locally

### Run Tests

```bash
./mvnw test
```

On Windows:

```bash
mvnw.cmd test
```

### Start the API

```bash
DB_PATH=./budgetflix.db ./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

On Windows PowerShell:

```powershell
$env:DB_PATH="./budgetflix.db"
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=prod"
```

Default API URL:

```text
http://localhost:8080
```

---

## Docker

The Docker image expects a prepared `app.jar` in the build context root.

```bash
./mvnw clean package -DskipTests
cp target/api.jar app.jar
docker build -t budgetflix-api .
docker run -p 8080:8080 budgetflix-api
```

The GitHub Actions workflow builds the JAR, creates a Docker image, and pushes it to GHCR on `main` branch push or pull request events:

```text
ghcr.io/budgetflix/budgetflix-api:dev
```

---

## Related Services

- `budgetflix` - frontend application
- `media-worker` - video processing and HLS generation
- `RabbitMQ` - upload job messaging
- `Nginx` - static HLS file serving
- remote host - real runtime data and media environment

---

## Roadmap

- authentication and authorization
- JWT-based stream access
- movie details endpoint
- search, filtering, and pagination
- admin endpoints
- media-worker status callback
- persisted HLS path handling
- cleaner production database configuration

---

## Status

Early-stage backend API. Movie listing, upload job publishing, RabbitMQ producer setup, and stream path lookup are already present.
