FROM gradle:8.10.2-jdk21 AS Builder

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=Builder /app/build/libs/*-.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]