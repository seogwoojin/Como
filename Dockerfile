FROM openjdk:18-ea-jdk-slim
VOLUME /tmp
COPY build/libs/demo-0.0.1-SNAPSHOT.jar codingtest-service.jar
ENTRYPOINT ["java","-jar","codingtest-service.jar"]