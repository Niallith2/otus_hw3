import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.otus.dto.AuthorDto;

import java.util.concurrent.ThreadLocalRandom;

import static ru.otus.handlers.ApiHandler.*;

@Epic("Тесты создания и получения авторов")
@Feature("REST")
public class RestAssuredTests {

    @Test
    @Story("Проверка GET /api/v1/Authors")
    public void getAuthorsTest() {
        getAuthors()
                .then()
                .statusCode(200);
    }

    @Test
    @Story("Проверка GET /api/v1/Authors/id")
    public void getAuthorTest() {
        getAuthor(1)
                .then()
                .statusCode(200);
    }

    @Test
    @Story("Негативная проверка GET /api/v1/Authors/id")
    public void negativeGetAuthorTest() {
        getAuthor(-1)
                .then()
                .statusCode(404);
    }

    @Test
    @Story("Проверка POST /api/v1/Authors")
    public void postAuthorsTest() {
        postAuthor(new AuthorDto(0, 0, "TestName", "TestMiddleName"))
                .then()
                .statusCode(200);
    }

    @Test
    @Story("Проверка создания и проверки созданного элемента")
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

    @Test
    @Story("Негативная проверка POST /api/v1/Authors")
    public void negativePostAuthorsTest() {
        postAuthor(new AuthorDto(999999999999999999L, 0, null, null))
                .then()
                .statusCode(400);
    }
}