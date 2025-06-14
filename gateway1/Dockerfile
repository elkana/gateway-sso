FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
ENV TZ="Asia/Jakarta"
# be careful reserved name application.yaml will replace the default application.yaml in the same jar location
# ENTRYPOINT ["java","-jar","app.jar", "--spring.profiles.active=uat", "--spring.config.additional-location=application.yaml"]
ENTRYPOINT ["java","-jar","app.jar", "--spring.profiles.active=uat"]
