FROM maven:3.8.3-openjdk-11-slim as BUILDER
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src/
RUN mvn clean package -Dmaven.test.skip
RUN ls -lha /build/target
FROM openjdk:11.0.8-jre-slim
WORKDIR /app/
COPY --from=BUILDER /build/target/quiz-backend-0.0.1-SNAPSHOT.jar /app/application.jar
CMD java -jar /app/application.jar