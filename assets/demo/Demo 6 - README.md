# Demo 6

Create Docker image using distribution ZIP and analyse it with dive

1. Build the project

   ```bash
   $ ./gradlew micronaut-layered-jar:clean micronaut-layered-jar:build
   ```

1. Navigate to the distributions directory

   ```bash
   $ cd micronaut-layered-jar/build/distributions
   ```

1. List the directory's content

   ```bash
   $ ls -la
   ```

   This should contain two archives

   ```bash
   -rw-r--r--   1 albertattard  staff  13690880 Apr 27 12:34 micronaut-layered-jar-1.0.tar
   -rw-r--r--   1 albertattard  staff  12149686 Apr 27 12:34 micronaut-layered-jar-1.0.zip
   ```

1. Unpack the archive

   ```bash
   $ rm micronaut-layered-jar-1.0.tar
   $ unzip micronaut-layered-jar-1.0.zip && rm micronaut-layered-jar-1.0.zip
   ```

   The archive contains our application and its dependencies

   ```bash
   Archive:  micronaut-layered-jar-1.0.zip
      creating: micronaut-layered-jar-1.0/
      creating: micronaut-layered-jar-1.0/lib/
     inflating: micronaut-layered-jar-1.0/lib/application.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-http-client-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-http-client-core-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-http-server-netty-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-http-server-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-runtime-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-validation-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-http-netty-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-websocket-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-router-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-http-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-aop-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-buffer-netty-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-inject-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/logback-classic-1.2.3.jar
     inflating: micronaut-layered-jar-1.0/lib/netty-handler-proxy-4.1.48.Final.jar
     inflating: micronaut-layered-jar-1.0/lib/netty-codec-http2-4.1.48.Final.jar
     inflating: micronaut-layered-jar-1.0/lib/netty-codec-http-4.1.48.Final.jar
     inflating: micronaut-layered-jar-1.0/lib/netty-handler-4.1.48.Final.jar
     inflating: micronaut-layered-jar-1.0/lib/netty-codec-socks-4.1.48.Final.jar
     inflating: micronaut-layered-jar-1.0/lib/netty-codec-4.1.48.Final.jar
     inflating: micronaut-layered-jar-1.0/lib/netty-transport-4.1.48.Final.jar
     inflating: micronaut-layered-jar-1.0/lib/netty-buffer-4.1.48.Final.jar
     inflating: micronaut-layered-jar-1.0/lib/netty-resolver-4.1.48.Final.jar
     inflating: micronaut-layered-jar-1.0/lib/netty-common-4.1.48.Final.jar
     inflating: micronaut-layered-jar-1.0/lib/jackson-datatype-jdk8-2.11.0.jar
     inflating: micronaut-layered-jar-1.0/lib/jackson-datatype-jsr310-2.11.0.jar
     inflating: micronaut-layered-jar-1.0/lib/jackson-databind-2.11.0.jar
     inflating: micronaut-layered-jar-1.0/lib/jackson-annotations-2.11.0.jar
     inflating: micronaut-layered-jar-1.0/lib/jackson-core-2.11.0.jar
     inflating: micronaut-layered-jar-1.0/lib/micronaut-core-2.0.0.jar
     inflating: micronaut-layered-jar-1.0/lib/javax.annotation-api-1.3.2.jar
     inflating: micronaut-layered-jar-1.0/lib/spotbugs-annotations-4.0.3.jar
     inflating: micronaut-layered-jar-1.0/lib/jsr305-3.0.2.jar
     inflating: micronaut-layered-jar-1.0/lib/snakeyaml-1.26.jar
     inflating: micronaut-layered-jar-1.0/lib/slf4j-api-1.7.26.jar
     inflating: micronaut-layered-jar-1.0/lib/rxjava-2.2.10.jar
     inflating: micronaut-layered-jar-1.0/lib/reactive-streams-1.0.3.jar
     inflating: micronaut-layered-jar-1.0/lib/validation-api-2.0.1.Final.jar
     inflating: micronaut-layered-jar-1.0/lib/logback-core-1.2.3.jar
     inflating: micronaut-layered-jar-1.0/lib/javax.inject-1.jar
      creating: micronaut-layered-jar-1.0/bin/
     inflating: micronaut-layered-jar-1.0/bin/micronaut-layered-jar
     inflating: micronaut-layered-jar-1.0/bin/micronaut-layered-jar.bat
   ```

1. Explore the directory

   ```bash
   $ cd micronaut-layered-jar-1.0
   $ ls -ls
   ```

   The archive contains two directories.

   ```bash
   drwxr-xr-x   4 albertattard  staff   128 Apr 27 12:34 bin
   drwxr-xr-x  43 albertattard  staff  1376 Apr 27 12:34 lib
   ```

   The expanded archive contains the following files

   ```bash
   $ tree .
   .
   ├── bin
   │   ├── micronaut-layered-jar
   │   └── micronaut-layered-jar.bat
   └── lib
       ├── application.jar
       ├── jackson-annotations-2.11.0.jar
       ├── jackson-core-2.11.0.jar
       ├── jackson-databind-2.11.0.jar
       ├── jackson-datatype-jdk8-2.11.0.jar
       ├── jackson-datatype-jsr310-2.11.0.jar
       ├── javax.annotation-api-1.3.2.jar
       ├── javax.inject-1.jar
       ├── jsr305-3.0.2.jar
       ├── logback-classic-1.2.3.jar
       ├── logback-core-1.2.3.jar
       ├── micronaut-aop-2.0.0.jar
       ├── micronaut-buffer-netty-2.0.0.jar
       ├── micronaut-core-2.0.0.jar
       ├── micronaut-http-2.0.0.jar
       ├── micronaut-http-client-2.0.0.jar
       ├── micronaut-http-client-core-2.0.0.jar
       ├── micronaut-http-netty-2.0.0.jar
       ├── micronaut-http-server-2.0.0.jar
       ├── micronaut-http-server-netty-2.0.0.jar
       ├── micronaut-inject-2.0.0.jar
       ├── micronaut-router-2.0.0.jar
       ├── micronaut-runtime-2.0.0.jar
       ├── micronaut-validation-2.0.0.jar
       ├── micronaut-websocket-2.0.0.jar
       ├── netty-buffer-4.1.48.Final.jar
       ├── netty-codec-4.1.48.Final.jar
       ├── netty-codec-http-4.1.48.Final.jar
       ├── netty-codec-http2-4.1.48.Final.jar
       ├── netty-codec-socks-4.1.48.Final.jar
       ├── netty-common-4.1.48.Final.jar
       ├── netty-handler-4.1.48.Final.jar
       ├── netty-handler-proxy-4.1.48.Final.jar
       ├── netty-resolver-4.1.48.Final.jar
       ├── netty-transport-4.1.48.Final.jar
       ├── reactive-streams-1.0.3.jar
       ├── rxjava-2.2.10.jar
       ├── slf4j-api-1.7.26.jar
       ├── snakeyaml-1.26.jar
       ├── spotbugs-annotations-4.0.3.jar
       └── validation-api-2.0.1.Final.jar
   ```

1. Run the application

   ```bash
   $ cd bin
   $ ls -ls
   ```

   There are two scripts, one for Linux/MAC and the other for Windows

   ```bash
   -rwxr-xr-x  1 albertattard  staff  7461 Apr 27 12:34 micronaut-layered-jar
   -rwxr-xr-x  1 albertattard  staff  4827 Apr 27 12:34 micronaut-layered-jar.bat
   ```

   Run the application

   ```bash
   $ ./micronaut-layered-jar
   ```

   Stop the application

1. Analyse the run script

   ```bash
   $ vi micronaut-layered-jar
   ```

   Note that the script builds the classpath (line 83)

   ```
   CLASSPATH=$APP_HOME/lib/application.jar:$APP_HOME/lib/micronaut-http-client-2.0.0.jar:$APP_HOME/lib/micronaut-http-client-core-2.0.0.jar:$APP_HOME/lib/micronaut-http-server-netty-2.0.0.jar:$APP_HOME/lib/micronaut-http-server-2.0.0.jar:$APP_HOME/lib/micronaut-runtime-2.0.0.jar:$APP_HOME/lib/micronaut-validation-2.0.0.jar:$APP_HOME/lib/micronaut-http-netty-2.0.0.jar:$APP_HOME/lib/micronaut-websocket-2.0.0.jar:$APP_HOME/lib/micronaut-router-2.0.0.jar:$APP_HOME/lib/micronaut-http-2.0.0.jar:$APP_HOME/lib/micronaut-aop-2.0.0.jar:$APP_HOME/lib/micronaut-buffer-netty-2.0.0.jar:$APP_HOME/lib/micronaut-inject-2.0.0.jar:$APP_HOME/lib/logback-classic-1.2.3.jar:$APP_HOME/lib/netty-handler-proxy-4.1.48.Final.jar:$APP_HOME/lib/netty-codec-http2-4.1.48.Final.jar:$APP_HOME/lib/netty-codec-http-4.1.48.Final.jar:$APP_HOME/lib/netty-handler-4.1.48.Final.jar:$APP_HOME/lib/netty-codec-socks-4.1.48.Final.jar:$APP_HOME/lib/netty-codec-4.1.48.Final.jar:$APP_HOME/lib/netty-transport-4.1.48.Final.jar:$APP_HOME/lib/netty-buffer-4.1.48.Final.jar:$APP_HOME/lib/netty-resolver-4.1.48.Final.jar:$APP_HOME/lib/netty-common-4.1.48.Final.jar:$APP_HOME/lib/jackson-datatype-jdk8-2.11.0.jar:$APP_HOME/lib/jackson-datatype-jsr310-2.11.0.jar:$APP_HOME/lib/jackson-databind-2.11.0.jar:$APP_HOME/lib/jackson-annotations-2.11.0.jar:$APP_HOME/lib/jackson-core-2.11.0.jar:$APP_HOME/lib/micronaut-core-2.0.0.jar:$APP_HOME/lib/javax.annotation-api-1.3.2.jar:$APP_HOME/lib/spotbugs-annotations-4.0.3.jar:$APP_HOME/lib/jsr305-3.0.2.jar:$APP_HOME/lib/snakeyaml-1.26.jar:$APP_HOME/lib/slf4j-api-1.7.26.jar:$APP_HOME/lib/rxjava-2.2.10.jar:$APP_HOME/lib/reactive-streams-1.0.3.jar:$APP_HOME/lib/validation-api-2.0.1.Final.jar:$APP_HOME/lib/logback-core-1.2.3.jar:$APP_HOME/lib/javax.inject-1.jar
   ```

1. Navigate to the libraries directory

   ```bash
   $ cd ../lib/
   $ ls -la
   ```

   The application JAR (not FatJAR) and its dependencies (and the transitive dependencies) are found here

   ```bash
   -rw-r--r--   1 albertattard  staff    10295 Apr 27 12:34 application.jar
   -rw-r--r--   1 albertattard  staff    68175 Apr 26 11:17 jackson-annotations-2.11.0.jar
   -rw-r--r--   1 albertattard  staff   351529 Apr 26 11:17 jackson-core-2.11.0.jar
   -rw-r--r--   1 albertattard  staff  1418028 Apr 26 11:17 jackson-databind-2.11.0.jar
   -rw-r--r--   1 albertattard  staff    34399 May 22 09:34 jackson-datatype-jdk8-2.11.0.jar
   -rw-r--r--   1 albertattard  staff   111072 May 22 09:34 jackson-datatype-jsr310-2.11.0.jar
   -rw-r--r--   1 albertattard  staff    26586 Aug 16 10:16 javax.annotation-api-1.3.2.jar
   -rw-r--r--   1 albertattard  staff     2497 Aug 16 10:16 javax.inject-1.jar
   -rw-r--r--   1 albertattard  staff    19936 Apr 25 13:58 jsr305-3.0.2.jar
   -rw-r--r--   1 albertattard  staff   290339 Apr  7 14:59 logback-classic-1.2.3.jar
   -rw-r--r--   1 albertattard  staff   471901 Apr  7 14:59 logback-core-1.2.3.jar
   -rw-r--r--   1 albertattard  staff    42054 Aug 16 10:16 micronaut-aop-2.0.0.jar
   -rw-r--r--   1 albertattard  staff     8785 Aug 16 10:17 micronaut-buffer-netty-2.0.0.jar
   -rw-r--r--   1 albertattard  staff  1546935 Aug 16 10:16 micronaut-core-2.0.0.jar
   -rw-r--r--   1 albertattard  staff   338080 Aug 16 10:16 micronaut-http-2.0.0.jar
   -rw-r--r--   1 albertattard  staff   133848 Aug 16 10:16 micronaut-http-client-2.0.0.jar
   -rw-r--r--   1 albertattard  staff   166331 Aug 16 10:16 micronaut-http-client-core-2.0.0.jar
   -rw-r--r--   1 albertattard  staff   178529 Aug 16 10:16 micronaut-http-netty-2.0.0.jar
   -rw-r--r--   1 albertattard  staff   121940 Aug 16 10:16 micronaut-http-server-2.0.0.jar
   -rw-r--r--   1 albertattard  staff   376673 Aug 16 10:16 micronaut-http-server-netty-2.0.0.jar
   -rw-r--r--   1 albertattard  staff   596209 Aug 16 10:16 micronaut-inject-2.0.0.jar
   -rw-r--r--   1 albertattard  staff   174273 Aug 16 10:17 micronaut-router-2.0.0.jar
   -rw-r--r--   1 albertattard  staff   670775 Aug 16 10:16 micronaut-runtime-2.0.0.jar
   -rw-r--r--   1 albertattard  staff   231040 Aug 16 10:16 micronaut-validation-2.0.0.jar
   -rw-r--r--   1 albertattard  staff    34395 Aug 16 10:16 micronaut-websocket-2.0.0.jar
   -rw-r--r--   1 albertattard  staff   278514 Aug 16 10:17 netty-buffer-4.1.48.Final.jar
   -rw-r--r--   1 albertattard  staff   320017 Aug 16 10:17 netty-codec-4.1.48.Final.jar
   -rw-r--r--   1 albertattard  staff   611413 Aug 16 10:17 netty-codec-http-4.1.48.Final.jar
   -rw-r--r--   1 albertattard  staff   456383 Aug 16 10:17 netty-codec-http2-4.1.48.Final.jar
   -rw-r--r--   1 albertattard  staff   119088 Aug 16 10:17 netty-codec-socks-4.1.48.Final.jar
   -rw-r--r--   1 albertattard  staff   622330 Aug 16 10:17 netty-common-4.1.48.Final.jar
   -rw-r--r--   1 albertattard  staff   453889 Aug 16 10:17 netty-handler-4.1.48.Final.jar
   -rw-r--r--   1 albertattard  staff    23852 Aug 16 10:16 netty-handler-proxy-4.1.48.Final.jar
   -rw-r--r--   1 albertattard  staff    33158 Aug 16 10:17 netty-resolver-4.1.48.Final.jar
   -rw-r--r--   1 albertattard  staff   473560 Aug 16 10:17 netty-transport-4.1.48.Final.jar
   -rw-r--r--   1 albertattard  staff    11369 Aug 16 10:16 reactive-streams-1.0.3.jar
   -rw-r--r--   1 albertattard  staff  2354699 Aug 16 10:16 rxjava-2.2.10.jar
   -rw-r--r--   1 albertattard  staff    41139 Aug 16 10:16 slf4j-api-1.7.26.jar
   -rw-r--r--   1 albertattard  staff   309001 May  7 16:39 snakeyaml-1.26.jar
   -rw-r--r--   1 albertattard  staff    15121 Aug 16 10:16 spotbugs-annotations-4.0.3.jar
   -rw-r--r--   1 albertattard  staff    93107 Aug 16 10:16 validation-api-2.0.1.Final.jar
   ```

1. Move the application to its own directory

   ```bash
   $ mkdir ../app
   $ mv application.jar ../app
   ```

   This is required as we like to separate the application from its dependencies

1. Navigate back to the `bin` directory and try to run the application

   ```bash
   $ cd ../bin
   $ ./micronaut-layered-jar
   ```

   This will fail as the run script is not properly configured

   ```bash
   Error: Could not find or load main class demo.micronaut.Application
   Caused by: java.lang.ClassNotFoundException: demo.micronaut.Application
   ```

1. Update the run script

   ```bash
   $ vi micronaut-layered-jar
   ```

   Replace the `$APP_HOME/lib/application.jar` with `$APP_HOME/app/application.jar` (on line 83)

   ```bash
   CLASSPATH=$APP_HOME/app/application.jar:$APP_HOME/lib/micronaut-http-client-2.0.0.jar:$APP_HOME/lib/micronaut-http-client-core-2.0.0.jar:$APP_HOME/lib/micronaut-http-server-netty-2.0.0.jar:$APP_HOME/lib/micronaut-http-server-2.0.0.jar:$APP_HOME/lib/micronaut-runtime-2.0.0.jar:$APP_HOME/lib/micronaut-validation-2.0.0.jar:$APP_HOME/lib/micronaut-http-netty-2.0.0.jar:$APP_HOME/lib/micronaut-websocket-2.0.0.jar:$APP_HOME/lib/micronaut-router-2.0.0.jar:$APP_HOME/lib/micronaut-http-2.0.0.jar:$APP_HOME/lib/micronaut-aop-2.0.0.jar:$APP_HOME/lib/micronaut-buffer-netty-2.0.0.jar:$APP_HOME/lib/micronaut-inject-2.0.0.jar:$APP_HOME/lib/logback-classic-1.2.3.jar:$APP_HOME/lib/netty-handler-proxy-4.1.48.Final.jar:$APP_HOME/lib/netty-codec-http2-4.1.48.Final.jar:$APP_HOME/lib/netty-codec-http-4.1.48.Final.jar:$APP_HOME/lib/netty-handler-4.1.48.Final.jar:$APP_HOME/lib/netty-codec-socks-4.1.48.Final.jar:$APP_HOME/lib/netty-codec-4.1.48.Final.jar:$APP_HOME/lib/netty-transport-4.1.48.Final.jar:$APP_HOME/lib/netty-buffer-4.1.48.Final.jar:$APP_HOME/lib/netty-resolver-4.1.48.Final.jar:$APP_HOME/lib/netty-common-4.1.48.Final.jar:$APP_HOME/lib/jackson-datatype-jdk8-2.11.0.jar:$APP_HOME/lib/jackson-datatype-jsr310-2.11.0.jar:$APP_HOME/lib/jackson-databind-2.11.0.jar:$APP_HOME/lib/jackson-annotations-2.11.0.jar:$APP_HOME/lib/jackson-core-2.11.0.jar:$APP_HOME/lib/micronaut-core-2.0.0.jar:$APP_HOME/lib/javax.annotation-api-1.3.2.jar:$APP_HOME/lib/spotbugs-annotations-4.0.3.jar:$APP_HOME/lib/jsr305-3.0.2.jar:$APP_HOME/lib/snakeyaml-1.26.jar:$APP_HOME/lib/slf4j-api-1.7.26.jar:$APP_HOME/lib/rxjava-2.2.10.jar:$APP_HOME/lib/reactive-streams-1.0.3.jar:$APP_HOME/lib/validation-api-2.0.1.Final.jar:$APP_HOME/lib/logback-core-1.2.3.jar:$APP_HOME/lib/javax.inject-1.jar
   ```

1. Run the application

   ```bash
   $ ./micronaut-layered-jar
   ```

   The application will now start

1. Analyse the `Dockerfile`

   ```bash
   $ cd ../../../..
   $ vi Dockerfile
   ```

   The `Dockerfile` contains the following instructions

   ```dockerfile
   FROM alpine:3.12.0 as builder
   WORKDIR /opt/app
   COPY ./build/distributions/*.zip application.zip
   RUN unzip application.zip \
       && rm application.zip \
       && mv * dist \
       && rm dist/bin/*.bat \
       && mv dist/bin/* dist/bin/run.original \
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

   This is more involved than the others.

   1. Extends from a Linux base image as all the required operations are carried out through the terminal

      ```dockerfile
      FROM alpine:3.12.0 as builder
      ```

   1. Copies the distribution archive into the Docker image work directory

      ```dockerfile
      WORKDIR /opt/app
      COPY ./build/distributions/*.zip application.zip
      ```

   1. Extracts the archive, updates the run script and moves the application to its own directory

      ```dockerfile
      RUN unzip application.zip \
          && rm application.zip \
          && mv * dist \
          && rm dist/bin/*.bat \
          && mv dist/bin/* dist/bin/run.original \
          && sed 's|$APP_HOME/lib/application.jar|$APP_HOME/app/application.jar|g' dist/bin/run.original > dist/bin/run \
          && chmod +x dist/bin/run \
          && rm dist/bin/run.original \
          && mkdir dist/app \
          && mv dist/lib/application.jar dist/app/application.jar
      ```

   1. Creates the Docker image that will be ued to run the application

      ```dockerfile
      FROM adoptopenjdk:8u262-b10-jre-hotspot
      ENV APP_HOME /opt/app
      WORKDIR ${APP_HOME}
      COPY --from=builder /opt/app/dist/lib lib/
      COPY --from=builder /opt/app/dist/bin bin/
      COPY --from=builder /opt/app/dist/app app/
      ENTRYPOINT ["./bin/run"]
      ```

      The application directory is the last directory copied as this is the layer that changes the most

1. Build the project

   ```bash
   $ cd ..
   $ ./gradlew micronaut-layered-jar:clean micronaut-layered-jar:build
   ```

1. Build the Docker image

   ```bash
   $ cd micronaut-layered-jar
   $ docker build . -t micronaut-layered-jar:local
   ```

1. Investigate the Docker image using `dive`

   ```bash
   $ dive micronaut-layered-jar:local
   ```

   ![dive micronaut-layered-jar](../images/dive-micronaut-layered-jar.png)
