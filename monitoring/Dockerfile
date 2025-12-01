FROM maven:3.9.11-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /app/target/monitoring-0.0.1-SNAPSHOT.jar ./

CMD [ "java", "-jar", "monitoring-0.0.1-SNAPSHOT.jar"]