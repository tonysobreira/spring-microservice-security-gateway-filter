FROM openjdk:17

EXPOSE 8082

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw mvnw.cmd pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]