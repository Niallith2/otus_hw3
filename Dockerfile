FROM maven:3.9.16-eclipse-temurin-17

ENV PROFILE="RestAssuredTests" \
    URL=$URL \
    MAVEN_OPTS="-Dmaven.resolver.transport=wagon \
    -Dmaven.wagon.http.ssl.ignore.validity.dates=true \
    -Dmaven.wagon.http.ssl.insecure=true"

WORKDIR /otus_hw3
COPY . .
RUN mvn dependency:go-offline

ENTRYPOINT ["./entrypoint.sh"]