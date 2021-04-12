FROM maven:3-jdk-11 AS build
ADD . /app
WORKDIR /app
RUN mvn clean install

FROM openjdk:11-jdk
MAINTAINER Yan Matskevich
VOLUME /tmp
COPY --from=build "/app/backend/target/english-helper-backend-0.0.1-SNAPSHOT.jar" app.jar
ENV JAVA_OPTS=""
# ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]