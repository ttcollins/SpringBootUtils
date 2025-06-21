# Todo App Module

## Overview
This module contains a minimal TODO application built with Spring Boot. It exposes
REST endpoints for managing tasks and their comments while capturing revision
history via Hibernate Envers.

## Required Dependencies
The module relies on the following dependencies as declared in `pom.xml`:

- `spring-boot-starter-web`
- `spring-data-envers`
- `spring-boot-starter-security`
- `spring-boot-starter-data-jpa` and the in-memory H2 database (inherited from the
  parent project)
- Test libraries: `spring-boot-starter-test` and `spring-security-test`

## Example Usage
Build and run the application from the `todoapp` directory:

```bash
mvn spring-boot:run
```

Create a task:

```bash
curl -X POST -H "Content-Type: application/json" \
  -d '{"title":"Test Task","description":"desc"}' \
  http://localhost:8080/api/tasks
```

Update the task and fetch its activity feed:

```bash
curl -X PUT -H "Content-Type: application/json" \
  -d '{"title":"Updated title","description":"desc","status":"COMPLETED"}' \
  http://localhost:8080/api/tasks/{id}

curl http://localhost:8080/api/tasks/{id}/activity
```

## Running Integration Tests
An integration test `TaskControllerIntegrationTest` verifies the API using
`MockMvc`. Execute it with Maven:

```bash
mvn test
```

To run only this test class:

```bash
mvn -Dtest=TaskControllerIntegrationTest test
```
