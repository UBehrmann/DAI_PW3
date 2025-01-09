FROM openjdk:21-jdk-slim
COPY crud_server/target/crud_server-1.0.jar app.jar
EXPOSE 7000
CMD ["java", "-jar", "app.jar"]