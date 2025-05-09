# ComplaintsService

**ComplaintsService** is a microservice responsible for managing user complaints.

### Technologies:
- **Java 21** – The programming language used to build the application.
- **Spring Boot** – The framework used to build the microservice.
- **Hibernate** – An ORM (Object-Relational Mapping) tool for interacting with the database.
- **PostgreSQL** – The relational database used to store application data.

### Requirements:
- **Podman** and **Podman Compose** to run the application in containers.

### Running the Application

To run the microservice, simply execute one command. Using **Podman Compose**, the entire infrastructure (PostgreSQL database and Spring Boot application) will be started in containers.

**Build and start the service**:

Make sure **Podman** and **Podman Compose** are installed on your system.

Run the following command from the project’s root directory, where the `docker-compose.yml` file is located:

```bash
podman-compose up
```

**Building the Image Manually**

If you'd like to build the application image manually, follow these steps:

Build the application: Run the following command in the project root:

```bash   
mvn clean package
```
    
Build the Docker image: Then run:
  
```bash   
podman build -t spring-app .
```

Run the application: After the image is built, you can run the application with:

```bash
podman run -d --name spring-app -p 8080:8080 spring-app
```

## API Documentation

Swagger UI is available at:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Monitoring Metrics with Spring Boot Actuator

Spring Boot Actuator provides several useful endpoints that can be used to monitor application performance, health, and metrics.

### Exposing Actuator Endpoints

To expose relevant Actuator endpoints, make sure they are enabled in the `application.yml` file.

### Available Endpoints

- `/actuator/metrics` – Displays all available metrics.
- `/actuator/health` – Displays the application's health status.
- `/actuator/info` – Displays basic application information (configured via `application.yml` or `application.properties`).
- `/actuator/metrics/http.server.requests` – Displays response time metrics for HTTP endpoints.
