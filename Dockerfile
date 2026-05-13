# Stage 1: Build the application using Maven
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B
COPY src src
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application using a minimal JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Add a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/*.jar app.jar

# Define persistent volumes for logs and uploads
VOLUME /tmp
VOLUME /app/uploads

EXPOSE 8080

# Use environment variables for sensitive data
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.profiles.active=prod"]
