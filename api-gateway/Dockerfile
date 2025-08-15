# Этап сборки
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn -pl api-gateway clean package -DskipTests

# Этап рантайма
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/api-gateway/target/*.jar api-gateway.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
