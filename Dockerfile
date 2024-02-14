FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/shop.jar ./shop.jar
EXPOSE 8080
CMD ["java", "-jar", "shop.jar"]