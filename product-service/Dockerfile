FROM openjdk:22-jdk
LABEL mentainer="jasbir008@gmail.com"
ARG JAR_FILE=/target/ecommerce-product-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} product-service.jar

ENTRYPOINT ["java","-jar","product-service.jar"]

EXPOSE 8191