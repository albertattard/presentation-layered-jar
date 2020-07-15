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

- A standard unit of software that packages up code and all its dependencies

.center[![Docker Container](assets/images/Docker Container.png)]

---

# Running a container

- We can run a docker container using the docker run command

  ```bash
  $ docker run -d --rm \
     --name docker-container-demo \
     -p 8080:8080 \
     spkane/quantum-game:latest
  ```

- The above command will start a game, which we can play

- We don't have to worry about any specific runtime environment or any particular dependency version

---

# Playing a game

.center[![Quantum Game with Photons](assets/images/Quantum%20Game%20with%20Photons.png)]

---

# What is a Docker Image?

lightweight, standalone, executable package of software that includes everything needed to run an application:
- code
- runtime
- system tools and libraries
- settings

- (based on Dockerfile instructions)
---

# What is a Dockerfile?
- script, composed of various commands (instructions)
- automatically perform actions on a base image in order to create a new one
- each instruction is a read-only layer
(add example dockerfile)

---

class: impact

# Layers
(Is this about layers in a Dockerfile?)
---

class: impact

# Fat JAR
- approach to packaging an application
- includes everything needed to run an app on a standard Java Runtime environment:
1) dependencies
2) resources
3) code

---

class: impact

# Layered JAR

---

class: impact

# Beyond Spring Boot

---

# Something else

```dockerfile
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

---

class: impact

# Thank You
## Feedback makes us better

Please send any feedback to: albert.attard@thoughtworks.com or jlang@thoughtworks.com

---

class: careers



