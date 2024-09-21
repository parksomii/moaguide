# Start from an OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Add a dummy build argument to invalidate the Docker cache for the application JAR file layer
# This forces Docker to re-copy the JAR every time, ensuring the latest version is always used.
ARG CACHEBUST=1

# Copy the JAR file built by the previous build step
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Set environment variables for the application
ARG PROFILES
ARG ENV

# Run the application with TrustStore settings
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-Djavax.net.ssl.trustStore=/usr/lib/jvm/java-17-openjdk-amd64/lib/security/cacerts", "-Djavax.net.ssl.trustStorePassword=changeit", "-jar", "/app/app.jar"]
