FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/orders-service*.jar orders-service.jar
EXPOSE 8003
ENTRYPOINT ["sh", "-c", "java -jar orders-service.jar"]