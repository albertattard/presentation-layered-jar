FROM adoptopenjdk:8u262-b10-jre-hotspot
WORKDIR /opt/app
COPY ./build/libs/*-all.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
