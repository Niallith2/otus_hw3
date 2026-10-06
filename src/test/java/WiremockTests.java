import com.github.tomakehurst.wiremock.WireMockServer;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.otus.dto.AuthorDto;
import ru.otus.wiremock.AuthorsStub;
import ru.otus.wiremock.WiremockHandler;

import java.util.concurrent.ThreadLocalRandom;

import static ru.otus.handlers.ApiHandler.getAuthor;
import static ru.otus.handlers.ApiHandler.postAuthor;

@Epic("Тесты создания и получения авторов с моком")
@Feature("REST")
public class WiremockTests {

    @BeforeClass
    public void init() {
        WiremockHandler.start(8089);
        AuthorsStub.createStubForGetAuthorById();
        AuthorsStub.createStubForPostAuthor();
    }

    @Test
    @Story("Проверка получения авторов с моком")
    public void getAuthorTest() {
        int id = ThreadLocalRandom.current().nextInt(1, 500000);
        AuthorDto authorDto = getAuthor(id)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(AuthorDto.class);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(authorDto.id, id);
        sa.assertEquals(authorDto.idBook, id);
        sa.assertEquals(authorDto.firstName, "First Name " + id);
        sa.assertEquals(authorDto.lastName, "Last Name " + id);
        sa.assertAll();
    }

    @Test
    @Story("Проверка создания автора с моком")
    public void postAuthorsTest() {
        int id = ThreadLocalRandom.current().nextInt(1, 500000);
        AuthorDto authorDto = postAuthor(new AuthorDto(id, id, "First Name " + id, "Last Name " + id))
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(AuthorDto.class);
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(authorDto.id, id);
        sa.assertEquals(authorDto.idBook, id);
        sa.assertEquals(authorDto.firstName, "First Name " + id);
        sa.assertEquals(authorDto.lastName, "Last Name " + id);
        sa.assertAll();
    }

}
