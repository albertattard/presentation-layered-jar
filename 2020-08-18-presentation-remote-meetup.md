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

- standard unit of software that packages up code and all its dependencies
- based on a Docker image

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
- text document that consists of instructions
- each instruction is a read-only layer

---

class: impact

# Layers

---

class: impact

# Fat JAR

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



