package ru.otus.handlers;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
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

    private static final String baseUrl = System.getProperty("api.baseUrl");
    private static RequestSpecification requestSpecification =
            new RequestSpecBuilder()
                    .setBaseUri(baseUrl)
                    .setContentType(ContentType.JSON)
                    .setBasePath("/api/v1")
                    .build()
                    .filter(new AllureRestAssured());

    @Step("Получить всех авторов")
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

    @Step("Получить автора по id = {id}")
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

    @Step("Создать автора")
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
