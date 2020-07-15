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
## Fat JAR
## Layered JAR
## Beyond Spring Boot

---

class: impact

# Docker

---

# What is a container?

- A standard deployment unit that encapsulates an application all its dependencies

.responsive[![Docker Container](assets/images/Docker Container.png)]

(Not happy with the title, and would like to have something more like _what is docker_ instead)

---

# How do create a docker container?

- A docker container is created every time we run a _docker image_

  ```bash
  $ docker run --rm \
     --name docker-container-demo \
     -p 8080:8080 \
     spkane/quantum-game:latest
  ```

- We don't have to worry about any specific runtime environment or any particular dependency version as everything is encapsulated in the container

---

.responsive[![Quantum Game with Photons](assets/images/Quantum%20Game%20with%20Photons.png)]

---

# What is a docker image?

- A docker image is a **read-only** file-system that contains

  - The operating system
  - The programs needed by the application, such as the Java Runtime Environment
  - The application executable, dependencies, and configuration

- A docker image is immutable and cannot be modified once built

- A new docker image is created every time a new version of our application is dockerized
---

# How do create a docker image?

- A docker image is created by building a _docker file_

  ```bash
  $ docker build . -t boot-fat-jar:local
  ```

  The above command creates a docker image and tags it as `boot-fat-jar:local`

- We can run the docker image (creating a docker container) once this is built

  ```bash
  $ docker run --rm \
     --name boot-fat-jar-demo \
     -p 8080:8080 \
     boot-fat-jar:local
  ```

---

# What is a docker file?

- A docker file is a text file, usually named `Dockerfile`, that contains a set of instructions used to create the docker image, that is, a file-system

- Docker promotes reuse and a _docker file_ can extend another image

  - For example, a _docker file_ hosting a Java application can extend another image that already has the Java Runtime installed and only customises the parts that it needs, rather that starting from scratch

---

# Example of a dockerfile

- Following is a typical `dockerfile` that hosts a Java application

  ```dockerfile
  FROM adoptopenjdk:8u252-b09-jre-hotspot-bionic
  WORKDIR /opt/app
  COPY ./build/libs/*.jar application.jar
  ENTRYPOINT ["java", "-jar", "application.jar"]
  ```

- Our example makes use of Java 8, as this is still the most popular version of Java, but will work with any version of Java

---

# Lifecycle

.responsive[![Docker Lifecycle](assets/images/Docker Lifecycle.png)]

---

class: impact

# Layers

---

# What are layers?

- Consider the following `Dockerfile`

  ```dockerfile
  FROM adoptopenjdk:8u252-b09-jre-hotspot-bionic
  WORKDIR /opt/app
  COPY ./build/libs/*.jar application.jar
  ENTRYPOINT ["java", "-jar", "application.jar"]
  ```

- The above docker file has four layers
  - `FROM ...`
  - `WORKDIR ...`
  - `COPY ...`
  - `ENTRYPOINT ...`

---

# Intermediate Images

.responsive[![Docker Layers](assets/images/Docker%20Layers.png)]

---

# Demo

- Build a docker image
- Discuss layers and see docker takes advantage of caching
- Analyse the docker image, using `dive`


(Demo 1 - Build docker image and analyse layers with dive.mp4)

---

# FatJAR / UberJAR

- A very common way to package a Java application is to create a FatJAR (or as also known an _UberJAR_)

- A FatJAR contains
  - Our application
  - All dependencies (JARs)

- A FatJAR is standalone and can be executed using `java -jar ...`

  ```bash
  $ java -jar application.jar
  ```

---

# FatJAR and docker image

- Our docker file copies the FatJAR

  ```dockerfile
  COPY ./build/libs/*.jar application.jar
  ```

- This creates a new layer every time a new FatJAR file is created

- Creating many large layers may consume large amounts of disk space

---

# Size of FatJAR

.responsive[![Size of FatJAR](assets/images/Size of FatJAR.png)]

---

# Space requirements

- Consider a relatively active team working 5 days a week and committing 20 times per day

- Each commit is followed by a push, which triggers an automated build pipeline, which builds the application and creates a new docker image

.responsive[![Size required after a week FatJAR.png](assets/images/Size required after a week FatJAR.png)]

(talk about: deleting older images is not a trivial task and requires some thought;
they might be needed for rollbacks or legal/auditing purposes)

---

# The challenge

- Our application comprise our code and its dependencies

- When new features are added, the dependencies are not neccessarly updated

- One small change in the code, creates a new docker layer of about 16MB in size

---

# The challenge

.responsive[![FatJAR Layers](assets/images/FatJAR Layers.png)]

(Should we have a quick demo here showing that the first two intermediate layers are cached but the third and the subsequent layers are not?)

---

# Splitting the dependencies

- Instead of using a FatJAR, we can split the dependencies

- We will have more than one docker `COPY` command, with our application copied last

- Changes to our application will simply require a thinner layer to be created

---

# Splitting the dependencies

.responsive[![Split Dependencies Layers](assets/images/Split Dependencies Layers.png)]

---

class: impact

# Layered JAR

---

class: impact

# SpringBoot Layered JAR

---

# Gradle.build

```groovy
bootJar {
  layered()
}
```

---

class: impact

# Demo SpringBoot layered JAR

(show how this code change is working)

---

# Changes in Dockerfile

```Dockerfile
FROM adoptopenjdk:8u252-b09-jre-hotspot-bionic as builder
WORKDIR /opt/app
COPY ./build/libs/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk:8u252-b09-jre-hotspot-bionic
WORKDIR /opt/app
COPY --from=builder /opt/app/dependencies ./
COPY --from=builder /opt/app/spring-boot-loader ./
COPY --from=builder /opt/app/snapshot-dependencies ./
COPY --from=builder /opt/app/application ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
```
(multi stage docker build; expand )

---

# Copy the layer JAR

```Dockerfile
FROM adoptopenjdk:8u252-b09-jre-hotspot-bionic as builder
WORKDIR /opt/app
COPY ./build/libs/*.jar application.jar
```

---

# Extract into layers

```Dockerfile
RUN java -Djarmode=layertools -jar application.jar extract
```

---

# Create the working image (need to get a better name)

```Dockerfile
FROM adoptopenjdk:8u252-b09-jre-hotspot-bionic
WORKDIR /opt/app
```
---

# Copy the layers

```Dockerfile
COPY --from=builder /opt/app/dependencies ./
COPY --from=builder /opt/app/spring-boot-loader ./
COPY --from=builder /opt/app/snapshot-dependencies ./
COPY --from=builder /opt/app/application ./
```

# Entrypoint

```Dockerfile
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
```

---

# Demo

- Create the image
- Inspect with Dive

---

# Comparison

| Commits|Without Layers|With Layers|
| --: | --: | --: |
| 1 | 16MB | 20KB |

---
class: impact

# Beyond Spring Boot
---

# Beyond Spring Boot

```Dockerfile
FROM alpine:3.12.0 as builder
WORKDIR /opt/app
COPY ./build/distributions/*.zip application.zip
RUN unzip application.zip \
    && rm application.zip \
    ...
    && mv dist/lib/application.jar dist/app/application.jar

FROM adoptopenjdk:8u252-b09-jre-hotspot-bionic
ENV APP_HOME /opt/app
WORKDIR ${APP_HOME}
COPY --from=builder /opt/app/dist/lib lib/
...
ENTRYPOINT ["./bin/run"]
```

---

# Demo

(Micronaut)

---

class: impact

# Thank You
## Feedback makes us better

Please send any feedback to: albert.attard@thoughtworks.com or jlang@thoughtworks.com

---

class: careers
