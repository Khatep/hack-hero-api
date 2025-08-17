FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
ARG MODULE
RUN mvn clean install -Dmaven.test.skip=true
RUN mvn -pl ${MODULE} spring-boot:repackage -Dmaven.test.skip=true

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ARG MODULE
COPY --from=builder /app/${MODULE}/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
