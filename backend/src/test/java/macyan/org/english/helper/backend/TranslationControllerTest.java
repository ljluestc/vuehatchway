package macyan.org.english.helper.backend;

import macyan.org.english.helper.backend.controller.TranslationController;
import macyan.org.english.helper.backend.service.translation.TranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.data.mongodb.host=localhost",
    "spring.data.mongodb.port=27017",
    "spring.data.mongodb.database=english-helper-test"
})
class TranslationControllerTest {

    @MockBean
    private TranslationService translationService;

    @Test
    void contextLoads() {
        assertNotNull(translationService);
    }
}
