FROM openjdk:19
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=out/artifacts/FeedbackCollectionSystem_jar/FeedbackCollectionSystem.jar
ADD ${JAR_FILE} FeedbackCollectionSystem.jar
ENTRYPOINT ["java","-jar","/FeedbackCollectionSystem.jar"]