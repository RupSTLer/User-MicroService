FROM openjdk:17
EXPOSE 9001
LABEL maintainer="RupSTLer"          
ADD target/user-microservice.jar user-microservice.jar
ENTRYPOINT ["java", "-jar", "user-microservice.jar"]
