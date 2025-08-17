# Этап сборки
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn -pl auth-module clean package -DskipTests

# Этап рантайма
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/auth-module/target/*.jar auth-module.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "auth-module.jar"]
