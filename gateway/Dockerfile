FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/gateway*.jar gateway.jar
EXPOSE 8888
ENTRYPOINT ["sh", "-c", "java -jar gateway.jar"]