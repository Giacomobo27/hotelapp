# Use OpenJDK as the base image
FROM openjdk:8-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the Maven target directory to the container
COPY target/*.jar app.jar

# Expose the port each service listens on
EXPOSE 8082

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
