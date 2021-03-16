FROM adoptopenjdk/openjdk11:alpine
COPY build/libs/teacherbot-backend-0.0.1-SNAPSHOT.jar teacherbot-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "teacherbot-backend-0.0.1-SNAPSHOT.jar"]