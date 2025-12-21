FROM gradle:9.2.1-jdk17 AS build

WORKDIR /setup

COPY . .

RUN gradle shadowJar --no-daemon

# Runtime
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the fat jar from the build stage
COPY --from=build /setup/build/libs/broadcast-app-1.0-all.jar /app/app.jar

ENV JSON_LOGGING=true

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
