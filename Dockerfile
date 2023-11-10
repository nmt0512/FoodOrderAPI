FROM maven:3.8.7-amazoncorretto-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

FROM openjdk:11
WORKDIR /app
COPY --from=build /app/target/FoodOrderAPI-0.0.1-SNAPSHOT.jar ./food-order-api.jar
EXPOSE 8080
CMD ["java", "-jar", "food-order-api.jar"]