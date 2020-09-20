title: Layered JARs
class: animation-fade
layout: true

<!-- This slide will serve as the base layout for all your slides -->
.bottom-bar[
  {{title}}
]
---

class: demand

---

class: impact

# {{title}}
## Optimise your Docker images with Layered JARs

---

class: impact

## By Johanna Lang and Albert Attard

*jlang@thoughtworks.com*
*albert.attard@thoughtworks.com*

---

# Agenda

## Docker
## Layers
## Spring Boot
## Beyond Spring Boot

---

class: impact

# Docker

---

# What is a Docker container?

- A standard deployment unit that encapsulates an application and all of its dependencies

  .responsive[![Docker Container](assets/images/Docker Container.png)]

---

# How is a Docker container created?

- By running a _Docker image_

  ```bash
  $ docker run \
      --rm \
      --name docker-container-demo \
      -p 8080:8080 \
      spkane/quantum-game:latest
  ```

  .conclusion[➤ We don't have to worry about any specific runtime environment or any particular dependency version as everything is encapsulated in the Docker container]

[//]: # (Notes)
[//]: # (`--rm` [Clean up](https://docs.docker.com/engine/reference/run/#clean-up---rm)/deletes the container once the container stops))
[//]: # (`--name` [Name](https://docs.docker.com/engine/reference/run/#name---name) the container)
[//]: # (`-p` binds a port on the host to a port on the container)

[//]: # (We were able to start the Quantum Game application without having to worry about anything.  This Quantum Game application could have been written in Java, Node, or assembly for all we care.  All we need is to have the Docker image and the rest is handled by Docker.)

---

# What is a Docker image?

- A **read-only** filesystem that contains

  - An operating system
  - The programs needed by the application (e.g. Java Runtime Environment)
  - The application executable, its dependencies, and configuration

- **Immutable** (cannot be modified once built)

  .conclusion[➤ New Docker image has to be created every time a new version of our application is built]

[//]: # (A Docker image is a file system with all the programs already install and all the files you need saved in the location you need them)
[//]: # (This is a good time to introduce dive, a tool for inspecting a Docker image)

---

# Demo

**Analyse a Docker image with _dive_**

- Overview of _dive_, a tool for exploring a Docker image and its contents
- Analyse a Docker image with _dive_

  ```bash
  $ dive spkane/quantum-game:latest
  ```

[//]: # (Demo 2)
[//]: # (Shows Docker image contents broken down by layer, we will show you later how to make use of it)

---

# How is a Docker image created?

- By building a _Dockerfile_

  ```bash
  $ docker build . -t boot-fat-jar:local
  ```

  The above command creates a Docker image and tags it as `boot-fat-jar:local`

- We can run this Docker image, creating a Docker container when doing so, using the given tag `boot-fat-jar:local`

  ```bash
  $ docker run \
       --rm \
       --name boot-fat-jar-demo \
       -p 8080:8080 \
       boot-fat-jar:local
  ```

[//]: # (Notes)
[//]: # (`--rm` [Clean up](https://docs.docker.com/engine/reference/run/#clean-up---rm)/deletes the container once the container stops))
[//]: # (`--name` [Name](https://docs.docker.com/engine/reference/run/#name---name) the container)
[//]: # (`-p` binds a port on the host to a port on the container)

---

# What is a _Dockerfile_?

- A text file, usually named `Dockerfile`, that contains a set of instructions

- So, the _Dockerfile_ is the source file used to create the Docker image

---

# How does a _Dockerfile_ look like?

- This is a simple _Dockerfile_ that hosts a Java 8 application

  ```dockerfile
  FROM adoptopenjdk:8u262-b10-jre-hotspot
  WORKDIR /opt/app
  COPY ./build/libs/*.jar application.jar
  ENTRYPOINT ["java", "-jar", "application.jar"]
  ```

[//]: # (Notes)
[//]: # (Docker runs instructions in a _Dockerfile_ in order)
[//]: # (`FROM`: A _Dockerfile_ must begin with a `FROM` instruction, which specifies the Parent Image from which you are building or start from `scratch`, known as [base image](https://docs.docker.com/develop/develop-images/baseimages/).)
[//]: # (`WORKDIR`: sets the working directory for the following commands.  This is the starting working directory when the container starts.)
[//]: # (`COPY`: copies the application jar file from the host (laptop) to the filesystem of the Docker image.)
[//]: # (`ENTRYPOINT`: runs the command with the given command line arguments, in this case a Java application)

---

# Lifecycle

.responsive[![Docker Lifecycle](assets/images/Docker Lifecycle.png)]

[//]: # (Notes)
[//]: # (Here you can see the whole Docker lifecycle that we just walked through starting from the end.)
[//]: # (The instructions inside the _Dockerfile_ build the Docker image which gets executed as a Docker container.)
[//]: # (For today's topic we will focus on the first two stages, _Dockerfile_ and Docker image.)

---

class: impact

# Layers

[//]: # (This brings us to the topic of layers.)

---

# What are layers?

- Remember our _Dockerfile_?

  ```dockerfile
  FROM adoptopenjdk:8u262-b10-jre-hotspot
  WORKDIR /opt/app
  COPY ./build/libs/*.jar application.jar
  ENTRYPOINT ["java", "-jar", "application.jar"]
  ```

- The above _Dockerfile_ has four layers

  1. `FROM ...`
  1. `WORKDIR ...`
  1. `COPY ...`
  1. `ENTRYPOINT ...`

[//]: # (It has 4 layers, each starting with an instruction. Basically, every line is a layer.)

---

# Intermediate Images

.responsive[![Docker Layers](assets/images/Docker%20Layers.png)]

[//]: # (Each executed instruction creates a layer which is also an intermediate image)
[//]: # (every instruction afterwards builds on the previous layer)
[//]: # (Switch to Albert for a Demo on how we build a Docker image and analyse its layers using dive)

---

# Demo

**Build docker image and analyse layers with _dive_**

- Build a Docker image
- Discuss layers and see Docker takes advantage of caching
- Analyse the Docker image, using _dive_

[//]: # (Demo 3)

---

# FatJAR

- A very common way to package a JVM based application is a **FatJAR**

- A FatJAR contains

  - The application
  - Resources that the application needs
  - The application dependencies

- It is standalone and can be executed using the following command

  ```bash
  $ java -jar application.jar
  ```

[//]: # (Notes)
[//]: # (A FatJAR is very convenient as packages the whole application into a single file.  All we need to do is distribute this file and run a simple command to start the application.)

---

# FatJAR in a Docker image

- The FatJAR is copied from our laptop into the Docker image using the `COPY` instruction

  ```dockerfile
  COPY ./build/libs/*.jar application.jar
  ```

- Every time the FatJAR is copied into a Docker image, a new layer is created

- Creating many large layers may consume large amounts of disk space

[//]: # (Notes)
[//]: # (As you can see in the next image)

---

# Size of FatJAR

.responsive[![Size of FatJAR](assets/images/Size of FatJAR.png)]

---

# Space requirements

- Consider a team working 5 days a week and committing code 20 times per day

- Each commit is followed by a push, which triggers an automated build pipeline, which builds the application and **creates a new docker image**

.responsive[![Size required after a week FatJAR.png](assets/images/Size required after a week FatJAR.png)]

[//]: # (Notes)
[//]: # (Deleting older images is not a trivial task because they might be needed for rollbacks or auditing purposes)

---

# Small changes result in large layers

- Our FatJAR contains our code **and** its dependencies

- When new features are added, the dependencies are not necessarily updated

  .conclusion[➤ However, each small change in the code creates a new docker layer of about 16MB in size]

---

# The FatJAR application - Version 1

.responsive[![FatJAR Layers](assets/images/FatJAR Layers - V1.png)]

[//]: # (Notes)
[//]: # (What does this mean in detail? We will visualize what happens every time the code changes.)
[//]: # (Here you can see the layer where the code change is happening)

---

# The FatJAR application - Version 2

.responsive[![FatJAR Layers](assets/images/FatJAR Layers - V2.png)]

[//]: # (Notes)
[//]: # (Even though Docker uses caching as shown in demo 3 and re-uses the first two layers)
[//]: # (every time the app code changes, two new layers are built, one of them containing the whole FatJAR)

---

# The FatJAR application - Version 3

.responsive[![FatJAR Layers](assets/images/FatJAR Layers - V3.png)]

[//]: # (Notes)
[//]: # (And as you can see, this adds up with every code commit, here we already have 8 layers)

---

# The FatJAR application - Version 4

.responsive[![FatJAR Layers](assets/images/FatJAR Layers - V4.png)]

[//]: # (Notes)
[//]: # (ending up with 10 layers for 4 code changes)

---

# An alternative approach

- Some parts of the FatJAR, such as the dependencies, change less frequently than others, yet take up most of the space

  .conclusion[➤ Solution: Separating the dependencies from the code by creating a new layer and taking advantage of Docker layer caching]

---

# Splitting the FatJAR - Version 1

.responsive[![Split Dependencies Layers](assets/images/Split Dependencies Layers - V1.png)]

[//]: # (Notes)
[//]: # (How will this work in detail?)
[//]: # (Dependencies and code now in two separate layers)

---

# Splitting the FatJAR - Version 2

.responsive[![Split Dependencies Layers](assets/images/Split Dependencies Layers - V2.png)]

[//]: # (Notes)
[//]: # (Here you can see that the dependencies from now on are getting cached, while the application layer contains our code without the dependencies)

---

# Splitting the FatJAR - Version 3

.responsive[![Split Dependencies Layers](assets/images/Split Dependencies Layers - V3.png)]

---

# Splitting the FatJAR - Version 4

.responsive[![Split Dependencies Layers](assets/images/Split Dependencies Layers - V4.png)]

[//]: # (So, from now on changes to our application will require a much thinner layer to be created)

---

class: impact

# Spring Boot

---

# Spring Boot

- Spring Boot is a very popular framework that promotes productivity

  .responsive[![Spring Boot](assets/images/Spring Boot.png)]  
  [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)

---

# Layered JAR

- Spring Boot 2.3 comes with a new feature, _Layered JAR_

- This can be enabled by simply adding a configuration to the Gradle `build.gradle` file

  ```groovy
  bootJar {
      layered()
  }
  ```

  An equivalent plugin is available for Maven too

- Spring Boot 2.4 will have _Layered JAR_ enabled by default

---

# How does this work?

- Build the Layered JAR (using _Gradle_ or _Maven_)

- Extract the Layered JAR (using _layertool_)

- Run the Extracted JAR (using _JarLauncher_)

.responsive[![From Code to Extracted JAR](assets/images/From Code to Extracted JAR.png)]

---

# Demo

**Build layered JAR, extract it and run extracted JAR**

- Build layered JAR
- Extract Layered JAR
- Run Extracted JAR

[//]: # (Demo 4)

---

# How does this work with Docker?

- We can take advantage of multistage Docker builds

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

---

# Builder stage

- Copy the layered JAR created by Gradle

  ```Dockerfile
  FROM adoptopenjdk:8u262-b10-jre-hotspot as builder
  WORKDIR /opt/app
  COPY ./build/libs/*.jar application.jar
  ```

- Extract the layered JAR

  ```Dockerfile
  RUN java -Djarmode=layertools -jar application.jar extract
  ```

  This command will create four folders in the builder stage, which we will copy in the final stage

---

# Final stage

- Starts with a Java image

  ```Dockerfile
  FROM adoptopenjdk:8u262-b10-jre-hotspot
  WORKDIR /opt/app
  ```

- Copy the extracted folders from the builder stage

  ```Dockerfile
  COPY --from=builder /opt/app/dependencies ./
  COPY --from=builder /opt/app/spring-boot-loader ./
  COPY --from=builder /opt/app/snapshot-dependencies ./
  COPY --from=builder /opt/app/application ./
  ```

- Run the application using `JarLauncher`

  ```Dockerfile
  ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
  ```

---

# Demo

**Create Docker image using layered JAR and analyse it with dive**

- Create Docker image, using multistage and layered JAR
- Inspect with _dive_

[//]: # (Demo 5)

---

# Size of Layered JAR

.responsive[![Size of Layered JAR](assets/images/Size of Layered JAR.png)]

---

# Comparison

- Comparing these two approaches we will find that a layered JAR is far more efficient than a FatJAR in terms of disk space

.responsive[![Comparing Layer Sizes](assets/images/Comparing Layer Sizes - Boot.png)]

---
class: impact

# Beyond Spring Boot

---

# Micronaut

- Micronaut is a reflection-free alternative framework to Spring Boot

  .responsive[![Micronaut](assets/images/Micronaut.png)]  
  [https://micronaut.io/](https://micronaut.io/)

---

# Lack of tooling

- Spring Boot provides the layered JAR functionality as a Gradle task

- This is not available for all other frameworks

- We can still take advantage of the multistage Docker build to split our dependencies from the application manually

---

# How will this work?

- Extract the distribution ZIP file, generated by the `distribution` Gradle plugin which is applied by the `application` Gradle plugin

- Move our application thin JAR to its own directory

- Update the run script

.responsive[![From Code to Distributed JARs](assets/images/From Code to Distributed JARs.png)]

---

# Multistage to the rescue!

```Dockerfile
FROM alpine:3.12.0 as builder
WORKDIR /opt/app
COPY ./build/distributions/*.zip application.zip
RUN unzip application.zip && rm application.zip \
    && mv * dist && rm dist/bin/*.bat && mv dist/bin/* dist/bin/run.original \
    && sed 's|$APP_HOME/lib/application.jar|$APP_HOME/app/application.jar|g' dist/bin/run.original > dist/bin/run \
    && chmod +x dist/bin/run \
    && rm dist/bin/run.original \
    && mkdir dist/app \
    && mv dist/lib/application.jar dist/app/application.jar

FROM adoptopenjdk:8u262-b10-jre-hotspot
ENV APP_HOME /opt/app
WORKDIR ${APP_HOME}
COPY --from=builder /opt/app/dist/lib lib/
COPY --from=builder /opt/app/dist/bin bin/
COPY --from=builder /opt/app/dist/app app/
ENTRYPOINT ["./bin/run"]
```

---

# Demo

**Create Docker image using distribution ZIP and analyse it with _dive_**

- Go through the `micronaut-layered-jar-1.0.zip` file
- Go through the multistage _Dockerfile_
- Create Docker image, using multistage
- Inspect with _dive_

[//]: # (Demo 6)

---

# Comparison

.responsive[![Comparing Layer Sizes](assets/images/Comparing Layer Sizes - Micronaut.png)]

---

# Clojure

- Clojure is a functional programming language that runs on the JVM

  .responsive[![Clojure](assets/images/Clojure.png)]  
  [https://clojure.org/](https://clojure.org/)

---

# No to UberJAR!!

- Clojure applications are typically packaged as an UberJAR, equivalent to a FatJAR

- Badigeon is a build library based on `tools.deps`, that can be used to create a slim JAR, a JAR file without dependencies

---

# Demo

**Create Clojure layered JAR Docker image using Badigeon and analyse it with _dive_**

- Go through the project layout
- Run the project locally
- Go through the multistage _Dockerfile_
- Create Docker image, using multistage
- Inspect with _dive_

[//]: # (Demo 7)

---

class: impact

# Thank You

## Feedback makes us better

Please send any feedback to: albert.attard@thoughtworks.com or jlang@thoughtworks.com

---

class: careers
