FROM openjdk:17

WORKDIR /app

RUN test -f target/shop.jar || { echo "Error: shop.jar not found"; exit 1; }

COPY target/shop.jar /app

COPY src/main/resources/db/migration /app/migrations

RUN java -jar shop.jar migrate

EXPOSE 8080

CMD ["java", "-jar", "shop.jar"]