FROM openjdk:22-jdk

ARG JAR_FILE=/target/ecommerce-discovery-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} discovery-service.jar

ENTRYPOINT ["java","-jar","discovery-service.jar"]

EXPOSE 8061