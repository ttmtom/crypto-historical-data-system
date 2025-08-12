# --- Stage 1: Build the application ---
# Use a Gradle image with JDK 21, as specified in build.gradle
FROM gradle:8.9-jdk21-alpine AS build

# Set the working directory for the build
WORKDIR /app

# Copy Gradle wrapper and build configuration files.
# This is done first to leverage Docker's layer caching for dependencies.
# Note: I'm assuming you have a settings.gradle file. If not, you can remove it from the COPY line.
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./

# Grant execute permission to the gradlew script
RUN chmod +x ./gradlew

# Download dependencies. This step is cached if build files haven't changed.
RUN ./gradlew dependencies --no-daemon

# Copy the rest of the application source code
COPY src ./src

# Build the application and create the executable JAR
# Skipping tests as they are not needed for the final image
RUN ./gradlew bootJar --no-daemon

# --- Stage 2: Create the final runtime image ---
# Use a slim Java Runtime Environment image for a smaller footprint
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built JAR from the build stage. Renaming it to app.jar for consistency.
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port the Spring Boot application listens on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]