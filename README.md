# EcoMindBackend

This project was generated using [Spring Boot](https://spring.io/projects/spring-boot) and Maven.

## Requirements

Before running the project, make sure you have:

- Java 21
- PostgreSQL
- Maven, or the included Maven Wrapper

## Database configuration

The development profile uses PostgreSQL with the following default values:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecomind
spring.datasource.username=postgres
spring.datasource.password=12345678
```

Create a local PostgreSQL database named `ecomind` or update the values in `src/main/resources/application-dev.properties` to match your environment.

## Development server

To start a local development server, run:

```bash
./mvnw spring-boot:run
```
Once the server is running, open your browser and navigate to `http://localhost:8092/`. The API will run using the `dev` profile by default.

## API documentation

Swagger UI is available at:

```bash
http://localhost:8092/swagger-ui/index.html
```

The OpenAPI JSON documentation is available at:

```bash
http://localhost:8092/v3/api-docs
```

## Running unit tests

To execute unit tests, use the following command:

```bash
./mvnw test
```

On Windows, you can run:

```bash
mvnw.cmd test
```

## Project structure

The backend is organized by bounded contexts and follows a layered structure:

- `profile`: users, families, friends and invitations
- `quests`: quests, activities and user progress
- `community`: communities, posts, events and registrations
- `ranking`: rankings and score entries
- `monetization`: cosmetics, gems, purchases and multipliers
- `shared`: common configuration, REST responses, validation and persistence utilities

## Additional Resources

For more information on the main tools used in this project, visit:

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/index.html)
- [Maven Wrapper Documentation](https://maven.apache.org/wrapper/)
- [Springdoc OpenAPI Documentation](https://springdoc.org/)
