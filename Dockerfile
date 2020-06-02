FROM openjdk:8-jdk-alpine
COPY ./target/usermicroservice-0.0.1.jar ./
CMD ["java","-jar","usermicroservice-0.0.1.jar"]
