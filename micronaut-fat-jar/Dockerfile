FROM adoptopenjdk:8u252-b09-jre-hotspot-bionic
WORKDIR /opt/app
COPY ./build/libs/*-all.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
