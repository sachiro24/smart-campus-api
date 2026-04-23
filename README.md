Smart Campus REST API (JAX-RS)

Overview

This project implements a **RESTful API** for managing a Smart Campus system using **JAX-RS (Jersey)**. The API allows management of:

* Rooms
* Sensors
* Sensor Readings

The system simulates a real-world backend where rooms contain sensors and sensors generate readings.

---

How to Run the Project

### 1. Build the project

```bash
mvn clean install
```

### 2. Run the server

```bash
mvn jetty:run
```

### 3. Base URL

```
http://localhost:8080/api/v1
```

---

## 🔗 API Endpoints

### 🔹 Discovery

* `GET /api/v1`

### 🔹 Rooms

* `GET /rooms`
* `POST /rooms`
* `GET /rooms/{id}`
* `DELETE /rooms/{id}`

### 🔹 Sensors

* `GET /sensors`
* `GET /sensors?type=CO2`
* `POST /sensors`

### 🔹 Sensor Readings

* `GET /sensors/{id}/readings`
* `POST /sensors/{id}/readings`

---

Sample CURL Commands

1. Get all rooms

```bash
curl -X GET http://localhost:8080/api/v1/rooms
```

2. Create a room

```bash
curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"R1","name":"Library","capacity":100}'
```

3. Create a sensor

```bash
curl -X POST http://localhost:8080/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"S1","type":"Temperature","status":"ACTIVE","currentValue":25.0,"roomId":"R1"}'
```

### 4. Filter sensors

```bash
curl -X GET "http://localhost:8080/api/v1/sensors?type=Temperature"
```

### 5. Add sensor reading

```bash
curl -X POST http://localhost:8080/api/v1/sensors/S1/readings \
-H "Content-Type: application/json" \
-d '{"id":"READ1","timestamp":1710000000000,"value":30.5}'
```

---

## ⚙️ Design Overview

The API follows REST principles:

* Resource-based structure
* Proper HTTP methods (GET, POST, DELETE)
* JSON communication
* Nested resources for readings

Data is stored in-memory using collections like `HashMap` and `ArrayList`.

#  REPORT (Theory Answers)

---

## 🔹 Part 1

### Q: JAX-RS Resource Lifecycle

By default, JAX-RS creates a **new instance per request**. This avoids shared state issues but requires careful handling of shared data structures. Using in-memory storage (like HashMaps) requires synchronization to prevent race conditions.

---

### Q: Why HATEOAS?

HATEOAS allows APIs to include links in responses, enabling clients to dynamically navigate resources. This reduces dependency on static documentation and improves flexibility.

---

## 🔹 Part 2

### Q: Returning IDs vs Full Objects

Returning only IDs reduces bandwidth but requires additional requests from clients. Returning full objects increases payload size but simplifies client-side processing.

---

### Q: Is DELETE idempotent?

Yes. Deleting the same resource multiple times produces the same result. After the first deletion, subsequent requests return the same state (resource not found).

---

## 🔹 Part 3

### Q: @Consumes JSON mismatch

If a client sends data in a different format (e.g., XML), JAX-RS will return **415 Unsupported Media Type** because it cannot process the request.

---

### Q: QueryParam vs PathParam

Query parameters are better for filtering because they are optional and flexible. Path parameters are better for identifying specific resources.

---

## 🔹 Part 4

### Q: Sub-resource Locator Benefits

It improves modularity by separating logic into smaller classes. This reduces complexity and makes the API easier to maintain.

---

### Q: Why update currentValue?

Updating `currentValue` ensures consistency between real-time data and historical readings.

---

## 🔹 Part 5

### Q: Why HTTP 422 instead of 404?

422 is more accurate because the request is valid but contains incorrect data (invalid roomId), whereas 404 is used for missing endpoints.

---

### Q: Risk of exposing stack traces

Stack traces expose internal system details such as class names and file paths, which can be exploited by attackers.

---

### Q: Why use Filters for logging?

Filters handle cross-cutting concerns centrally, avoiding repetitive logging code in every method.

---
 Error Handling Implemented

409 Conflict → Room not empty
422 Unprocessable Entity → Invalid room reference
403 Forbidden → Sensor in maintenance
500 Internal Server Error → Unexpected errors

---

Logging

Logs all incoming requests (method + URI)
Logs all outgoing responses (status code)

Conclusion

This project demonstrates:

* RESTful API design
* Resource relationships
* Error handling with Exception Mappers
* Filtering and sub-resources
* Logging and observability

---
