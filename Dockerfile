FROM maven:3.9.6-amazoncorretto-21 as builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk

COPY --from=builder /app/target/api-0.0.1-SNAPSHOT.jar /api-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/api-0.0.1-SNAPSHOT.jar"]
