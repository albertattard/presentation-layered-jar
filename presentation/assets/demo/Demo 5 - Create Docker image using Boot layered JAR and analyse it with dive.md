# Demo 5

Create Docker image using Boot layered JAR and analyse it with dive

1. Go to example

   ```bash
   $ cd boot-layered-jar
   ```

1. Build the project

   ```bash
   $ ./gradlew clean build
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

1. Build the Docker image

   ```bash
   $ docker build . -t boot-layered-jar:local
   ```

   This time we have two images, the builded image used only while building the Docker image and the final image

   ```bash
   Sending build context to Docker daemon  17.13MB
   Step 1/11 : FROM adoptopenjdk:8u262-b10-jre-hotspot as builder
    ---> e0c6010ca29d
   Step 2/11 : WORKDIR /opt/app
    ---> Using cache
    ---> 8f41a4f785be
   Step 3/11 : COPY ./build/libs/*.jar application.jar
    ---> d0b689509daa
   Step 4/11 : RUN java -Djarmode=layertools -jar application.jar extract
    ---> Running in 38f03e4be023
   Removing intermediate container 38f03e4be023
    ---> 76cb557df0df
   Step 5/11 : FROM adoptopenjdk:8u262-b10-jre-hotspot
    ---> e0c6010ca29d
   Step 6/11 : WORKDIR /opt/app
    ---> Using cache
    ---> 8f41a4f785be
   Step 7/11 : COPY --from=builder /opt/app/dependencies ./
    ---> 84313e71a385
   Step 8/11 : COPY --from=builder /opt/app/spring-boot-loader ./
    ---> 47ef00ab3db1
   Step 9/11 : COPY --from=builder /opt/app/snapshot-dependencies ./
    ---> ba0de72a7498
   Step 10/11 : COPY --from=builder /opt/app/application ./
    ---> 1f87a403fc44
   Step 11/11 : ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
    ---> Running in c2580ee5c2f6
   Removing intermediate container c2580ee5c2f6
    ---> c8c4588d2fa9
   Successfully built c8c4588d2fa9
   Successfully tagged boot-layered-jar:local
   ```

1. Rebuild the Layered JAR without changing the code and Build the image again

   Regenerate the Layered JAR without changing the code

   ```bash
   $ ./gradlew clean bootJar
   ```

   Build the image again

   ```bash
   $ docker build . -t boot-layered-jar:local
   ```

   Note that this time, all four layers (in the second image) were copied from cache

   ```bash
   Sending build context to Docker daemon  17.02MB
   Step 1/11 : FROM adoptopenjdk:8u262-b10-jre-hotspot as builder
    ---> e0c6010ca29d
   Step 2/11 : WORKDIR /opt/app
    ---> Using cache
    ---> 8f41a4f785be
   Step 3/11 : COPY ./build/libs/*.jar application.jar
    ---> a19f27b6f6bc
   Step 4/11 : RUN java -Djarmode=layertools -jar application.jar extract
    ---> Running in 7992cd636d02
   Removing intermediate container 7992cd636d02
    ---> 9d2aaeec0d38
   Step 5/11 : FROM adoptopenjdk:8u262-b10-jre-hotspot
    ---> e0c6010ca29d
   Step 6/11 : WORKDIR /opt/app
    ---> Using cache
    ---> 8f41a4f785be
   Step 7/11 : COPY --from=builder /opt/app/dependencies ./
    ---> Using cache
    ---> 84313e71a385
   Step 8/11 : COPY --from=builder /opt/app/spring-boot-loader ./
    ---> Using cache
    ---> 47ef00ab3db1
   Step 9/11 : COPY --from=builder /opt/app/snapshot-dependencies ./
    ---> Using cache
    ---> ba0de72a7498
   Step 10/11 : COPY --from=builder /opt/app/application ./
    ---> Using cache
    ---> 1f87a403fc44
   Step 11/11 : ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
    ---> Using cache
    ---> c8c4588d2fa9
   Successfully built c8c4588d2fa9
   Successfully tagged boot-layered-jar:local
   ```

1. Investigate the Docker image using `dive`

   ```bash
   $ dive boot-layered-jar:local
   ```

   ![dive boot-layered-jar](../images/dive-boot-layered-jar.png)
