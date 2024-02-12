FROM openjdk:17

WORKDIR /app
COPY target/shop.jar /app
EXPOSE 8080
CMD ["java", "-jar", "shop.jar"]