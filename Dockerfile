# Start from an OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file built by the previous build step
# Replace "*.jar" with the specific JAR file name, if needed, or leave the wildcard if necessary.
ARG JAR_FILE=build/libs/*.jar
COPY --chown=root:root ${JAR_FILE} /app/app.jar

# Set environment variables for the application
ARG PROFILES
ARG ENV

# Run the application with TrustStore settings
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-Djavax.net.ssl.trustStore=/usr/lib/jvm/java-17-openjdk-amd64/lib/security/cacerts", "-Djavax.net.ssl.trustStorePassword=changeit", "-jar", "/app/app.jar"]
