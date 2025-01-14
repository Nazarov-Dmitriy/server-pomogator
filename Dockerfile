FROM openjdk:17-alpine

EXPOSE 5500

ADD target/serverPomogator-0.0.1-SNAPSHOT.jar pomogator.jar

COPY target/serverPomogator-0.0.1-SNAPSHOT.jar pomogator.jar

ENTRYPOINT  ["java", "-jar", "/pomogator.jar"]

CMD ["bash"]

