import org.testng.annotations.Test;
import ru.otus.dto.AuthorDto;

import static ru.otus.handlers.ApiHandler.*;
import static org.hamcrest.Matchers.*;

public class RestAssuredTests {

    //Проверка GET /api/v1/Authors
    @Test
    public void getAuthorsTest(){
        getAuthors()
                .then()
                .statusCode(200);
    }

    //Проверка GET /api/v1/Authors/id
    @Test
    public void getAuthorTest(){
        getAuthor(1)
                .then()
                .statusCode(200);
    }

    //Негативная проверка GET /api/v1/Authors/id
    @Test
    public void negativeGetAuthorTest(){
        getAuthor(-1)
                .then()
                .statusCode(404);
    }

    //Проверка POST /api/v1/Authors
    @Test
    public void postAuthorsTest(){
        postAuthor(new AuthorDto(0, 0, "TestName", "TestMiddleName"))
                .then()
                .statusCode(200);
    }

    //Негативная проверка POST /api/v1/Authors
    @Test
    public void negativePostAuthorsTest(){
        postAuthor(new AuthorDto(999999999999999999L, 0, null, null))
                .then()
                .statusCode(400);
    }

}
