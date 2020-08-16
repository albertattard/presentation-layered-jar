# Demo 2

1. Build the project

   ```bash
   $ ./gradlew boot-layered-jar:clean boot-layered-jar:build
   ```

1. Navigate to the build directory, where the application FatJAR resides

   ```bash
   $ cd boot-layered-jar/build/libs
   ```

1. List the directory

   ```bash
   $ ls -la
   ```

   This should only contains the FatJAR

   ```bash
   -rw-r--r--  1 albertattard  staff  16552730 Apr 27 12:34 boot-layered-jar-1.0.jar
   ```

1. Run the application

   ```bash
   $ java -jar boot-layered-jar-1.0.jar
   ```

   Stop the application

1. Use the _layertools_ mode

   ```bash
   $ java -Djarmode=layertools -jar boot-layered-jar-1.0.jar
   ```

   This will list all running options

   ```bash
   Usage:
     java -Djarmode=layertools -jar boot-layered-jar-1.0.jar

   Available commands:
     list     List layers from the jar that can be extracted
     extract  Extracts layers from the jar for image creation
     help     Help about any command
   ```

1. List the layers

   ```bash
   $ java -Djarmode=layertools -jar boot-layered-jar-1.0.jar list
   ```

   Our demo is using the default four layers

   ```bash
   dependencies
   spring-boot-loader
   snapshot-dependencies
   application
   ```

1. Extract the layers into separate directories

   ```bash
   $ java -Djarmode=layertools -jar boot-layered-jar-1.0.jar extract
   ```

1. Delete the application FatJAR

   ```bash
   $ rm boot-layered-jar-1.0.jar
   ```

   The directory should not contain four directories

   ```bash
   $ ls -la
   ```

1. Try to run the application

   ```bash
   $ java org.springframework.boot.loader.JarLauncher
   ```

   This will fail as our application is not properly structured

   ```bash
   Error: Could not find or load main class org.springframework.boot.loader.JarLauncher
   Caused by: java.lang.ClassNotFoundException: org.springframework.boot.loader.JarLauncher
   ```

1. We need to move the contents of each directory into one directory

   ```bash
   $ cp -R dependencies/* . && rm -rf dependencies
   $ cp -R spring-boot-loader/* . && rm -rf spring-boot-loader
   $ rm -rf snapshot-dependencies
   $ cp -R application/* . && rm -rf application
   ```

   List the directory content

   ```bash
   $ ls -la
   ```

   There should be three directories

   ```bash
   drwxr-xr-x  6 albertattard  staff  192 Apr 27 12:34 BOOT-INF
   drwxr-xr-x  3 albertattard  staff   96 Apr 27 12:34 META-INF
   drwxr-xr-x  3 albertattard  staff   96 Apr 27 12:34 org
   ```

1. Run the application

   ```bash
   $ java org.springframework.boot.loader.JarLauncher
   ```

   The application will now start
