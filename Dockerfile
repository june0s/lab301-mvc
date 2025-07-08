# 1. maven install -> war 파일 생성
FROM maven:3.8.5-eclipse-temurin-8-alpine AS build

WORKDIR /build
COPY pom.xml .
COPY src ./src

RUN mvn package -Dmaven.test.skip=true

# 2. docker 이미지 생성
FROM tomcat:8.5.54-jdk8-corretto

RUN rm -Rf /usr/local/tomcat/webapps/*
COPY --from=build /build/target/lab301-mvc-tutor-4.2.0.war /usr/local/tomcat/webapps/ROOT.war
