FROM openjdk:22-jdk

ARG JAR_FILE=/target/ecommerce-config-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} config-service.jar

ENTRYPOINT ["java","-jar","config-service.jar"]

EXPOSE 8088