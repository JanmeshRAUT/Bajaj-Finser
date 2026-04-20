# Spring Boot Webhook Application

This is a Spring Boot application that processes webhooks for SQL problem-solving tasks as part of a hiring challenge.

## Overview

The application performs the following steps automatically on startup:

1. **Webhook Generation**: Sends a POST request to `https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA` with user credentials
2. **Problem Solving**: Based on the response, determines whether to solve Question 1 (odd last two digits) or Question 2 (even last two digits)
3. **Solution Submission**: Submits the SQL query solution to the provided webhook URL using JWT authentication

## Technology Stack

- **Java Version**: 17 LTS
- **Spring Boot Version**: 3.5.14-SNAPSHOT
- **Build Tool**: Maven (with wrapper)
- **Key Dependencies**:
  - Spring Boot Web Starter
  - Spring Boot Test Starter

## Project Structure

```
src/
├── main/
│   └── java/com/example/webhook_app/
│       ├── WebhookAppApplication.java          # Main application class with RestTemplate bean
│       ├── dto/
│       │   ├── GenerateWebhookRequest.java     # DTO for webhook generation request
│       │   ├── GenerateWebhookResponse.java    # DTO for webhook generation response
│       │   └── TestWebhookRequest.java         # DTO for solution submission
│       ├── service/
│       │   └── WebhookService.java             # Core service handling webhook logic
│       └── listener/
│           └── WebhookStartupListener.java     # Event listener triggered on app startup
└── test/
    └── java/com/example/webhook_app/
        └── WebhookAppApplicationTests.java     # Application tests
```

## Key Features

- **No Controllers/Endpoints**: The application is triggered purely by Spring Boot lifecycle events
- **JWT Support**: Uses access tokens provided by the API in Authorization headers
- **RestTemplate Integration**: Uses Spring's RestTemplate for HTTP communication
- **Automatic Startup**: Webhook processing initiates automatically when the application starts

## Building and Running

### Build the Application

```bash
./mvnw clean package
```

### Run the Application

```bash
java -jar target/webhook-app-0.0.1-SNAPSHOT.jar
```

Or use Maven directly:

```bash
./mvnw spring-boot:run
```

### Running Tests

```bash
./mvnw clean test
```

## Configuration

User credentials used in the webhook generation:
- **Name**: Janmesh Nitin Raut
- **Registration Number**: ADT23SOCB1593
- **Email**: janmeshraut11@gmail.com

The registration number's last two digits (93) are odd, so the application solves Question 1.

## Implementation Details

### WebhookService

The main service that orchestrates the entire webhook workflow:

1. `generateWebhook()`: Makes the initial API call to generate a webhook URL and access token
2. `solveSQLProblem()`: Determines the question based on odd/even and returns the appropriate SQL query
3. `submitSolution()`: Submits the SQL query solution to the webhook URL with JWT authentication

### WebhookStartupListener

Listens to the `ApplicationStartedEvent` and triggers `webhookService.processWebhook()` automatically when the application starts.

## Sample Output

When the application runs successfully, you should see output similar to:

```
Application started. Processing webhook...
Webhook URL: https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA
Access Token: eyJhbGciOiJIUzI1NiJ9...
Solution submitted successfully. Response: {"success":true,"message":"Webhook processed successfully"}
```

## Requirements Met

- ✅ Spring Boot application with automatic startup webhook processing
- ✅ POST request to generate webhook with specified credentials
- ✅ SQL problem solving based on registration number (odd/even)
- ✅ JWT token usage in Authorization header for solution submission
- ✅ RestTemplate for HTTP communication
- ✅ No controller/endpoint required - purely event-driven

## Notes

- The application uses Spring Boot's built-in RestTemplate for HTTP requests
- SSL certificate validation is performed for secure connections
- The application logs all webhook interactions to the console
- Exception handling ensures graceful degradation if API calls fail

## Contact

For questions about the implementation, refer to the source code comments or the test cases in `WebhookAppApplicationTests.java`.
