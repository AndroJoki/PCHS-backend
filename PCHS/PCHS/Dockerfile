
FROM sapmachine:21 AS build
COPY ..
RUN mvn clean package -DskipTests


FROM openjdk:21-slim
COPY --from=build /target/PCHS-0.0.1-SNAPSHOT.jar PCHS.jar
EXPOSE 18080
ENTRYPOINT ["java", "-jar", "PCHS.jar"]