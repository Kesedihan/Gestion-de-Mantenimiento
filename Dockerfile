### Multi-stage Dockerfile for building and running the Spring Boot backend
# Build stage: use Maven to build the project
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

# copy maven wrapper and pom first to leverage layer caching
COPY mvnw pom.xml ./

# copy source
COPY src ./src

# Build the war (skip tests for faster builds; remove -DskipTests for CI with tests)
RUN chmod +x mvnw || true
RUN mvn -B -DskipTests package

# Run stage: use a small JRE image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Change this path if artifactId/version change in pom
ARG JAR_FILE=target/proyectoGestionMantCorrectivo-0.0.1-SNAPSHOT.war
COPY --from=build /workspace/${JAR_FILE} app.war

ENV JAVA_OPTS="-Xms256m -Xmx512m"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.war"]
