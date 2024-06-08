# Stage 1: Build the Svelte app
FROM node:20-alpine AS svelte-builder

## Set the working directory
WORKDIR /app

## Copy package.json and package-lock.json
COPY frontend/package*.json ./

## Install dependencies
RUN npm install

## Copy the rest of the application code
COPY frontend/ ./

## Build the Svelte app
RUN npm run build

# Stage 2: Build the Java backend
FROM maven:3.9.7-eclipse-temurin-21 AS java-builder

# Set the working directory
WORKDIR /app

## Copy the Maven/Gradle build files
COPY backend/pom.xml ./

## Install dependencies
RUN mvn dependency:resolve

## Copy the rest of the application code
COPY backend/ ./

## Package the Java application
RUN mvn package -DskipTests

# Stage 3: Create the final image
FROM eclipse-temurin:21-jdk-alpine

## Set the working directory
WORKDIR /app

# Copy the packaged Java application
COPY --from=java-builder /app/target/*.jar ./app.jar

# Copy the built Svelte app
COPY --from=svelte-builder /app/build ./public

# Expose the port the app runs on
EXPOSE 8080

# Run the Java application
ENTRYPOINT ["java", "-jar", "./app.jar"]
