package macyan.org.english.helper;

import io.restassured.RestAssured;
import macyan.org.english.helper.util.RandomPortInitializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = EnglishHelperApplication.class
)
@ContextConfiguration(initializers = RandomPortInitializer.class)
public abstract class EnglishHelperApplicationFunctionalTest {

    @LocalServerPort
    protected int serverPort;

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
    }

    @Test
    public void testContextIsUp() {
        Assert.assertTrue(true);
    }
}
