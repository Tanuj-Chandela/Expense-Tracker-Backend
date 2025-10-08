# Use Amazon Corretto 21 with platform specification
FROM --platform=linux/amd64 amazoncorretto:21

WORKDIR /app

# Copy the JAR file from the host to the container
COPY build/libs/auth-0.0.1-SNAPSHOT.jar /app/jars/app.jar

# Expose the port that your Java service listens on
EXPOSE 9898

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "/app/jars/app.jar"]