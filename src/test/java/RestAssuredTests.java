import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.otus.dto.AuthorDto;

import java.util.concurrent.ThreadLocalRandom;

import static ru.otus.handlers.ApiHandler.*;

public class RestAssuredTests {

    //Проверка GET /api/v1/Authors
    @Test
    public void getAuthorsTest() {
        getAuthors()
                .then()
                .statusCode(200);
    }

    //Проверка GET /api/v1/Authors/id
    @Test
    public void getAuthorTest() {
        getAuthor(1)
                .then()
                .statusCode(200);
    }

    //Негативная проверка GET /api/v1/Authors/id
    @Test
    public void negativeGetAuthorTest() {
        getAuthor(-1)
                .then()
                .statusCode(404);
    }

    //Проверка POST /api/v1/Authors
    @Test
    public void postAuthorsTest() {
        postAuthor(new AuthorDto(0, 0, "TestName", "TestMiddleName"))
                .then()
                .statusCode(200);
    }

    //Проверка создания и проверки созданного элемента
    @Test
    public void createAndGetAuthor() {
        int randId = ThreadLocalRandom.current().nextInt(1, 500);
        String firstname = "First Name " + randId;
        String lastname = "Last Name " + randId;
        postAuthor(new AuthorDto(randId, randId, firstname, lastname))
                .then()
                .statusCode(200);
        AuthorDto author = getAuthor(randId).as(AuthorDto.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(author.id, randId);
        softAssert.assertEquals(author.firstName, firstname);
        softAssert.assertEquals(author.lastName, lastname);
        softAssert.assertAll();
    }

    //Негативная проверка POST /api/v1/Authors
    @Test
    public void negativePostAuthorsTest() {
        postAuthor(new AuthorDto(999999999999999999L, 0, null, null))
                .then()
                .statusCode(400);
    }

}
