FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/medical-clinic-proxy-0.0.1-SNAPSHOT.jar medical-clinic-proxy.jar
ENTRYPOINT ["java", "-jar", "medical-clinic-proxy.jar"]