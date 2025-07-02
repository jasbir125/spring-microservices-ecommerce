FROM openjdk:22-jdk

ARG JAR_FILE=/target/ecommerce-gateway-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} gateway-service.jar

ENTRYPOINT ["java","-jar","gateway-service.jar"]

EXPOSE 8060