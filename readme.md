# Realtime Cargo Tracking System (Backend)

* This is the backend service for the Realtime Cargo Tracking application. It is built with **Spring Boot** and uses **Apache Kafka** for high-throughput event streaming, **PostgreSQL** for data persistence, and **WebSockets** (STOMP) to push realtime location updates to the frontend.

## Tech Stack

* **Java 17+** (Spring Boot 3.x)
* **PostgreSQL** (Relational Database)
* **Apache Kafka** (Event Streaming)
* **Docker & Docker Compose** (Containerization for Kafka)
* **WebSocket / STOMP** (Realtime Communication)

---

## Setup & Installation

Follow these steps in order to start the application.

### 1. Database Setup (PostgreSQL)

You need a local PostgreSQL instance running.

1.  Open your database management tool (pgAdmin, DBeaver, or Terminal).
2.  Create a new database named `realtime`:
```sql
CREATE DATABASE realtime;
```
### 2. Configure Credentials
You must update the database username and password in the configuration file.

Navigate to:
```sql 
src/main/resources/application.properties
```
Update the following lines with your local PostgreSQL credentials:

Properties
****
```sql
spring.datasource.url=jdbc:postgresql://localhost:5432/realtime********
spring.datasource.username=postgres      <-- CHANGE THIS
spring.datasource.password=your_password <-- CHANGE THIS
spring.jpa.hibernate.ddl-auto=update
```
3. Start Kafka (Docker)
The docker-compose.yaml file is located inside the source package. You need to navigate there to start Kafka and Zookeeper.

Open your terminal in the project root.

Navigate to the directory containing the docker file:

```Bash

cd src/main/java/com/realtime
```
Start the containers:

```Bash

docker-compose up -d
```
Verify: Ensure that the containers are running (docker ps). Kafka runs on port 9092.

4. Run the Application
