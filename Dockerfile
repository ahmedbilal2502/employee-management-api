# Use the official Eclipse Temurin image for JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the Maven build artifact (the JAR file) into the container
COPY target/employee-management-0.0.1-SNAPSHOT.jar employee-management.jar

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "employee-management.jar"]
