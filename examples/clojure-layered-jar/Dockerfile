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
