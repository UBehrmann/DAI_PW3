FROM openjdk:21-jdk-slim
LABEL org.opencontainers.image.source="ghcr.io/ubehrmann/crud_server"
COPY crud_server/target/crud_server-1.0.jar app.jar
EXPOSE 7000
CMD ["java", "-jar", "app.jar"]