FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/\*.jar
COPY ${JAR_FILE} market-api.jar
ENTRYPOINT ["java","-jar","/market-api.jar"]