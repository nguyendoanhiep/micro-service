FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/product-service-*.jar product-service.jar
EXPOSE 8002
ENTRYPOINT ["sh", "-c", "java -jar product-service.jar"]