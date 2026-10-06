package ru.otus.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.qameta.allure.Step;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class AuthorsStub {

    @Step("Создать мок на получение автора по id")
    public static void createStubForGetAuthorById() {
        WiremockHandler
                .getWireMockServer()
                .stubFor(
                        WireMock.get(
                                        WireMock.urlPathTemplate("/api/v1/Authors/{id}"))
                                .willReturn(aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("{\n" +
                                                "  \"id\": {{request.pathSegments.id}},\n" +
                                                "  \"idBook\": {{request.pathSegments.id}},\n" +
                                                "  \"firstName\": \"First Name {{request.pathSegments.id}}\",\n" +
                                                "  \"lastName\": \"Last Name {{request.pathSegments.id}}\"\n" +
                                                "}")
                                        .withTransformers("response-template")));
    }

    @Step("Создать мок на создание автора")
    public static void createStubForPostAuthor() {
        WiremockHandler
                .getWireMockServer()
                .stubFor(
                        WireMock.post(WireMock.urlPathTemplate("/api/v1/Authors"))
                                .willReturn(aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("{\n" +
                                                "  \"id\": {{jsonPath request.body '$.id'}},\n" +
                                                "  \"idBook\": {{jsonPath request.body '$.idBook'}},\n" +
                                                "  \"firstName\": \"{{jsonPath request.body '$.firstName'}}\",\n" +
                                                "  \"lastName\": \"{{jsonPath request.body '$.lastName'}}\"\n" +
                                                "}")
                                        .withTransformers("response-template")));
    }

}
