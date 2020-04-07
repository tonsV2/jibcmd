FROM adoptopenjdk/openjdk14:x86_64-alpine-jdk-14_36 AS builder
#FROM azul/zulu-openjdk-alpine:14 AS builder
WORKDIR /src

COPY *.gradle *.properties gradlew ./
COPY gradle gradle
RUN ./gradlew --version

COPY . .
RUN ./gradlew jpackageImage

FROM alpine
RUN apk --no-cache add gcompat
WORKDIR /app
COPY --from=builder /src/build/jpackage/jibcmd .
CMD exec ./bin/jibcmd
