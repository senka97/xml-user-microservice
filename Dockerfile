FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/usermicroservice-0.0.1.jar usermicroservice-0.0.1.jar
RUN chmod +x /entrypoint.sh
CMD ["/entrypoint.sh"]
