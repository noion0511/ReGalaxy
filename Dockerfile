FROM openjdk:8-jdk

WORKDIR /home/docker

ARG JAR_FILE=phonesin/build/libs/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]