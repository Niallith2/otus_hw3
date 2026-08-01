package ru.otus.handlers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.otus.dto.AuthorDto;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.notNullValue;

public class ApiHandler {

    private static final String baseUrl = "https://fakerestapi.azurewebsites.net";
    private static RequestSpecification requestSpecification =
            new RequestSpecBuilder()
                    .setBaseUri(baseUrl)
                    .setContentType(ContentType.JSON)
                    .setBasePath("/api/v1")
                    .build();

    public static Response getAuthors() {
        String url = "/Authors";
        return given()
                .log().all()
                .spec(requestSpecification)
                .when()
                .get(url)
                .then()
                .body(not(emptyString()))
                .body(notNullValue())
                .log().all()
                .extract().response();
    }

    public static Response getAuthor(int id) {
        String url = "/Authors/" + id;
        return given()
                .log().all()
                .spec(requestSpecification)
                .when()
                .get(url)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response postAuthor(AuthorDto author) {
        String url = "/Authors";
        return given()
                .log().all()
                .spec(requestSpecification)
                .body(author)
                .when()
                .post(url)
                .then()
                .log().all()
                .extract().response();
    }

}
