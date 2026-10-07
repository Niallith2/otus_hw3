FROM maven:3.9.16-eclipse-temurin-17

ENV PROFILE="RestAssuredTests" \
    MAVEN_OPTS="-Dmaven.resolver.transport=wagon \
    -Dmaven.wagon.http.ssl.ignore.validity.dates=true \
    -Dmaven.wagon.http.ssl.insecure=true"

WORKDIR /otus_hw3
COPY . .
RUN mvn dependency:go-offline && \
    chmod +x /otus_hw3/entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]