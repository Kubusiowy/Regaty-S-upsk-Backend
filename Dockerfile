FROM gradle:9.3.0-jdk21 AS builder

WORKDIR /app

COPY . .

RUN gradle clean shadowJar -x test --no-daemon

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*-all.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]