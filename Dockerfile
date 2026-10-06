FROM maven:3.9.16-eclipse-temurin-17

ENV PROFILE="RestAssuredTests"
ENV URL="https://fakerestapi.azurewebsites.net"

WORKDIR /otus_hw3
COPY . .
RUN mvn dependency:go-offline

ENTRYPOINT ["./entrypoint.sh"]