FROM openjdk:19
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=/*.jar
ADD ${JAR_FILE} app.jar
CMD ["java","-jar","/app.jar"]
#or entrypoint idk