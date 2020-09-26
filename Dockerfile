FROM gradle:6.4.1-jdk11
COPY . .
RUN ./gradlew clean build
CMD ["java", "-agentlib:jdwp=server=y,transport=dt_socket,address=*:8003,suspend=n", "-jar", "./build/libs/task04-0.0.1-SNAPSHOT.jar"]