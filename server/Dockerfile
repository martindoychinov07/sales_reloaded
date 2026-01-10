# Use JDK image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Spring Boot jar (already contains React dist)
COPY target/sales-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8081

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
