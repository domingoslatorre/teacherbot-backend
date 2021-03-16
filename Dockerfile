FROM adoptopenjdk/openjdk11:alpine
ADD build/libs/teacherbot-backend-0.0.1-SNAPSHOT.jar teacherbot-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "teacherbot-backend-0.0.1-SNAPSHOT.jar"]