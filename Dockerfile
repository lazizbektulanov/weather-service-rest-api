FROM maven:3.9-amazoncorretto-11 as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM amazoncorretto:11
EXPOSE 8080
WORKDIR /app
COPY --from=builder /app/target/weather-service.jar /app/weather-service.jar
ENTRYPOINT ["java","-jar","/app/weather-service.jar"]