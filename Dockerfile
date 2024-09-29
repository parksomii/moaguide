# Start from an OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Set environment variables for the application
ARG PROFILES
ARG ENV

# Add a dummy build argument to invalidate the Docker cache for the application JAR file layer
# This is optional and can be adjusted or removed
ARG CACHEBUST=1
RUN echo "Cache bust at $(date)" # This is mainly for cache busting when needed

# Copy only the dependencies first to leverage Docker cache
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Pre-cache dependencies (this will only rerun if dependencies change)
RUN ./gradlew build --no-daemon --parallel --stacktrace

# Copy the application source code
COPY src /app/src

# Build the application
RUN ./gradlew build --no-daemon --parallel --stacktrace

# Copy the JAR file
ARG JAR_FILE=build/libs/*.jar
COPY --chown=root:root ${JAR_FILE} /app/app.jar

# Run the application with TrustStore settings
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-Djavax.net.ssl.trustStore=/usr/lib/jvm/java-17-openjdk-amd64/lib/security/cacerts", "-Djavax.net.ssl.trustStorePassword=changeit", "-jar", "/app/app.jar"]
