# Demo 1

Run a Docker image

1. Verify that Docker is installed

   ```bash
   $ docker --version
   ```

   Docker's version is printed if Docker is installed.

   ```bash
   Docker version 19.03.12, build 48a66213fe
   ```

   The version may vary.

   Follow the installation instructions from [here](https://docs.docker.com/get-docker/), should you need to install Docker.

1. Find the Docker image

   Search for ["quantum-game" in Docker repository](https://hub.docker.com/search?q=quantum-game&type=image)

   The Docker image `spkane/quantum-game` is the most popular option.

1. Start the Docker image

   ```bash
   $ docker run \
       --rm \
       --name docker-container-demo \
       -p 8080:8080 \
       spkane/quantum-game:latest
   ```

   Wait for the image to be downloaded.

   ```bash
   Unable to find image 'spkane/quantum-game:latest' locally
   latest: Pulling from spkane/quantum-game
   605ce1bd3f31: Pull complete
   17b6aecea465: Pull complete
   b9c40c340829: Pull complete
   59cfa8d7b067: Pull complete
   4910a4ac74ee: Pull complete
   bc233c62e092: Downloading [============================>                      ]  42.28MB/73.74MB
   ```

   The Docker image will start automatically once downloaded.

   ```bash
   Status: Downloaded newer image for spkane/quantum-game:latest
   Starting up http-server, serving .
   Available on:
     http://127.0.0.1:8080
     http://172.17.0.2:8080
   Hit CTRL-C to stop the server
   ```

   This Docker image points to an older version of the game: [https://quantumgame.io/](https://quantumgame.io/).

1. Access the game from the browser: [http://localhost:8080](http://localhost:8080)

   ![Quantum Game with Photons](../assets/images/Quantum%20Game%20with%20Photons.png)

   Have fun!!
