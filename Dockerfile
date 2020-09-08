FROM oracle/graalvm-ce:20.2.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/micronaut-graal-app
WORKDIR /home/app/micronaut-graal-app

RUN native-image -cp entrypoints/build/libs/*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/micronaut-graal-app/kotlin-cleanarch-api /app/micronaut-graal-app
ENTRYPOINT ["/app/micronaut-graal-app"]