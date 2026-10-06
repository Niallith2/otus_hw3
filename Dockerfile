FROM maven:3.9.16-eclipse-temurin-17

ENV PROFILE="RestAssuredTests"
ENV URL="https://fakerestapi.azurewebsites.net"

WORKDIR /otus_hw3
COPY . .
RUN mvn dependency:go-offline -Dmaven.wagon.http.ssl.ignore.validity.dates=true

ENTRYPOINT ["./entrypoint.sh"]