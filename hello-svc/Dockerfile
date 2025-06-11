# Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package -DskipTests

# Package stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
ENV TZ="Asia/Jakarta"
# beware port conflict. app-gui starts on 26__, app-service starts on 25__
# EXPOSE 2501
ENTRYPOINT ["java","-jar","app.jar", "--spring.profiles.active=dev"]
