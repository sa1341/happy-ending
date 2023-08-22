FROM ${HOST}/base/eclipse-temurin:17
LABEL MAINTAINER="tommy.bear"

ADD --chown=deploy:deploy build/libs/happy-ending-athena-1.0.0.jar app.jar

EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar ./app.jar
