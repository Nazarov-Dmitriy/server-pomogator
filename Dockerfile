FROM openjdk:17-alpine

EXPOSE 5500

ADD target/serverPomogator-0.0.1-SNAPSHOT.jar pomogator.jar

COPY target/serverPomogator-0.0.1-SNAPSHOT.jar pomogator.jar

ADD keystore.p12 /etc/letsencrypt/live/server.xn----8sbisvlbcdqnh.xn--p1ai/keystore.p12

ENTRYPOINT  ["java", "-jar", "/pomogator.jar"]

CMD ["bash"]