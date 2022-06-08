# syntax = docker/dockerfile:experimental
FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S devfinder -G spring
VOLUME /data
RUN mkdir -p /data
RUN chown devfinder /data
VOLUME /logs
RUN mkdir -p /logs
RUN chown devfinder /logs
USER devfinder:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} dev-finder.jar
EXPOSE 8888/tcp

ENTRYPOINT ["java","-jar","/dev-finder.jar"]