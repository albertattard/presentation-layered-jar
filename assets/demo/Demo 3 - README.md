# Demo 3

Create Docker image using layered JAR and analyse it with dive

1. Build the project

   ```bash
   $ ./gradlew boot-layered-jar:clean boot-layered-jar:build
   ```

1. Navigate to the project directory

   ```bash
   $ cd boot-layered-jar
   ```

1. Analyse the multistage `Dockerfile`

   ```bash
   $ vi Dockerfile
   ```

   The `Dockerfile` contains the following instructions

   ```dockerfile
   FROM adoptopenjdk:8u262-b10-jre-hotspot as builder
   WORKDIR /opt/app
   COPY ./build/libs/*.jar application.jar
   RUN java -Djarmode=layertools -jar application.jar extract

   FROM adoptopenjdk:8u262-b10-jre-hotspot
   WORKDIR /opt/app
   COPY --from=builder /opt/app/dependencies ./
   COPY --from=builder /opt/app/spring-boot-loader ./
   COPY --from=builder /opt/app/snapshot-dependencies ./
   COPY --from=builder /opt/app/application ./
   ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
   ```

   1. Copies the layered FatJAR and extract it

      ```dockerfile
      FROM adoptopenjdk:8u262-b10-jre-hotspot as builder
      WORKDIR /opt/app
      COPY ./build/libs/*.jar application.jar
      RUN java -Djarmode=layertools -jar application.jar extract
      ```

   1. Extends from an existing Java image and creates a directory where the application will be copied to

      ```dockerfile
      FROM adoptopenjdk:8u262-b10-jre-hotspot
      WORKDIR /opt/app
      ```

   1. Copy the content of each directory into the workdir

      ```dockerfile
      COPY --from=builder /opt/app/dependencies ./
      COPY --from=builder /opt/app/spring-boot-loader ./
      COPY --from=builder /opt/app/snapshot-dependencies ./
      COPY --from=builder /opt/app/application ./
      ```

      Note that the contents of each directory is copied into tha same directory

   1. Set the command that will be executed when the Docker container starts

      ```dockerfile
      ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
      ```

1. Build the Docker image for the first time

   ```bash
   $ docker build . -t boot-layered-jar:local
   ```

1. Investigate the Docker image using `dive`

   ```bash
   $ dive boot-layered-jar:local
   ```

   ![dive boot-layered-jar](../images/dive-boot-layered-jar.png)
