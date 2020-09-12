FROM clojure:openjdk-8-tools-deps-1.10.1.561-slim-buster
WORKDIR /opt/app
COPY ./deps.edn ./deps.edn
RUN clj -e :ok
COPY ./src ./src
ENTRYPOINT ["clj", "-m", "layeredjar"]
