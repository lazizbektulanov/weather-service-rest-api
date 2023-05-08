FROM amazoncorretto:11
EXPOSE 8080
WORKDIR /app
ADD target/weather-service.jar /app/weather-service.jar
ENTRYPOINT ["java","-jar","/app/weather-service.jar"]