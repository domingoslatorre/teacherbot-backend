FROM adoptopenjdk/openjdk11:alpine-jre
COPY build/libs/teacherbot-backend-0.0.1-SNAPSHOT.jar teacherbot-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xmx384m", "-Xms384m", "-Xss512k", "-XX:+UseCompressedOops", "-Dserver.port=$PORT", "-jar", "teacherbot-backend-0.0.1-SNAPSHOT.jar"]


