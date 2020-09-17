# Demo 7

Create Clojure layered JAR Docker image using Badigeon and analyse it with dive

1. Go to example

   ```bash
   $ cd clojure-layered-jar
   ```

1. Analyse project directory

   ```bash
   $ tree .
   ```

   We have two Clojure files

   ```bash
   .
   ├── Dockerfile
   ├── build_src
   │   └── package.clj
   ├── deps.edn
   └── src
       └── layered_jar
           └── main.clj

   3 directories, 4 files
   ```

   The `build_src/package.clj` is used build and prepare our application

   ```clojure
   (ns package
     (:require [badigeon.bundle :refer [bundle make-out-path]]
               [badigeon.compile :as c]))

   (defn -main []
     (bundle (make-out-path 'lib nil))
     (c/compile 'layered-jar.main {:compile-path "target/classes"}))
   ```

1. Build the project

   ```bash
   $ rm -rf .cpcache && rm -rf target
   $ clojure -A:build -m package
   ```

1. Navigate to the `target` directory

   ```bash
   $ cd target
   ```

   List the directory's content

   ```bash
   $ ls -l
   ```

   This should contain two folders

   ```bash
   drwxr-xr-x  10 albertattard  staff  320 Apr 27 12:34 classes
   drwxr-xr-x   4 albertattard  staff  128 Apr 27 12:34 lib
   ```

1. Navigate to the `classes` directory

   ```bash
   $ cd classes
   ```

   List the directory's content

   ```bash
   $ ls -l
   ```

   This will contain all Clojure compiled code, including our application

   ```bash
   total 0
   drwxr-xr-x  136 albertattard  staff  4352 Apr 27 12:34 cheshire
   drwxr-xr-x  303 albertattard  staff  9696 Apr 27 12:34 clj_time
   drwxr-xr-x    5 albertattard  staff   160 Apr 27 12:34 clojure
   drwxr-xr-x   68 albertattard  staff  2176 Apr 27 12:34 cognitect
   drwxr-xr-x   14 albertattard  staff   448 Apr 27 12:34 crypto
   drwxr-xr-x    3 albertattard  staff    96 Apr 27 12:34 io
   drwxr-xr-x   10 albertattard  staff   320 Apr 27 12:34 layered_jar
   drwxr-xr-x    4 albertattard  staff   128 Apr 27 12:34 ring
   ```

1. Run the application

   ```bash
   $ cd ..
   $ java -cp "classes:lib/lib/*" layered_jar.main
   ```

   Stop the application

1. Move the application to its own directory

   ```bash
   $ mkdir app
   $ mv classes/layered_jar app/layered_jar
   ```

   This is required as we like to separate the application from its dependencies.

   The `target` directory should have three directories

   ```bash
   $ ls -l
   drwxr-xr-x  3 albertattard  staff   96 Apr 27 12:34 app
   drwxr-xr-x  9 albertattard  staff  288 Apr 27 12:34 classes
   drwxr-xr-x  4 albertattard  staff  128 Apr 27 12:34 lib
   ```

   Our application should be under the `app` directory

   ```bash
   $ tree app
   app
   └── layered_jar
       ├── main$_main.class
       ├── main$create_server.class
       ├── main$fn__13715.class
       ├── main$loading__6721__auto____149.class
       ├── main$respond_hello.class
       ├── main$start.class
       ├── main.class
       └── main__init.class

   1 directory, 8 files
   ```

1. Run the application

   ```bash
   $ java -cp "app:classes:lib/lib/*" layered_jar.main
   ```

   The application should still run.  Stop the application

1. Analyse the `Dockerfile`

   ```bash
   $ cd ..
   $ vi Dockerfile
   ```

   The `Dockerfile` contains the following instructions

   ```dockerfile
   FROM clojure:openjdk-8-tools-deps-1.10.1.561-slim-buster AS builder
   WORKDIR /opt/app
   COPY deps.edn ./deps.edn
   COPY ./build_src ./build_src
   COPY ./src ./src
   RUN clojure -A:build -m package
   RUN mkdir ./target/app && mv ./target/classes/layered_jar ./target/app/layered_jar

   FROM adoptopenjdk:8u262-b10-jre-hotspot
   WORKDIR /opt/app
   COPY --from=builder /opt/app/target/lib/lib ./lib
   COPY --from=builder /opt/app/target/classes ./classes
   COPY --from=builder /opt/app/target/app ./app
   ENTRYPOINT ["java", "-cp", "app:classes:lib/*", "layered_jar.main"]
   ```

1. Build the Docker image

   ```bash
   $ docker build . -t clojure-layered-jar:local
   ```

1. Investigate the Docker image using `dive`

   ```bash
   $ dive clojure-layered-jar:local
   ```

   ![dive micronaut-layered-jar](../images/dive-clojure-layered-jar.png)
