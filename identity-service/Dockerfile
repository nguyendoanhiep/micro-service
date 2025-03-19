FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/identity-service*.jar identity-service.jar
EXPOSE 8001
ENTRYPOINT ["sh", "-c", "java -jar identity-service.jar"]