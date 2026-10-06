FROM maven:3.9.16-eclipse-temurin-17

ENV PROFILE="RestAssuredTests" \
    URL="https://fakerestapi.azurewebsites.net" \
    MAVEN_OPTS="-Dmaven.wagon.http.ssl.ignore.validity.dates=true -Dmaven.wagon.http.ssl.insecure=true"

WORKDIR /otus_hw3
COPY . .
RUN mvn dependency:go-offline

ENTRYPOINT ["./entrypoint.sh"]