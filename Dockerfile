# Start from an OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file built by the previous build step
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Set environment variables for the application
ARG PROFILES
ARG ENV

# Run the application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-jar", "/app/app.jar"]
