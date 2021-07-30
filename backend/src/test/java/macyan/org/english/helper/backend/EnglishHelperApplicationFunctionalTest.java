package macyan.org.english.helper.backend;

import io.restassured.RestAssured;
import macyan.org.english.helper.backend.configuration.EnglishHelperProperties;
import macyan.org.english.helper.backend.domain.user.Role;
import macyan.org.english.helper.backend.domain.user.RoleRepository;
import macyan.org.english.helper.backend.domain.user.RoleType;
import macyan.org.english.helper.backend.domain.user.User;
import macyan.org.english.helper.backend.domain.user.UserRepository;
import macyan.org.english.helper.backend.util.RandomPortInitializer;
import macyan.org.english.helper.backend.util.UserUtility;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = EnglishHelperApplication.class
)
@ContextConfiguration(initializers = RandomPortInitializer.class)
public abstract class EnglishHelperApplicationFunctionalTest {

    protected static final String ADMIN_NAME = "admin";
    protected static final String ADMIN_EMAIL = "admin@test.com";
    protected static final String ADMIN_PASS = "admin12345";

    protected static final String USER_NAME = "user";
    protected static final String USER_EMAIL = "user@test.com";
    protected static final String USER_PASS = "user12345";

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected EnglishHelperProperties properties;

    @Autowired
    protected MongoTemplate template;

    @LocalServerPort
    protected int serverPort;

    @Before
    public void setUp() {
        RestAssured.port = serverPort;

        UserUtility.initAllRoles(roleRepository);
        UserUtility.createUser(
            roleRepository,
            userRepository,
            passwordEncoder,
            RoleType.ROLE_ADMIN,
            ADMIN_NAME,
            ADMIN_EMAIL,
            ADMIN_PASS
        );
        UserUtility.createUser(
            roleRepository,
            userRepository,
            passwordEncoder,
            RoleType.ROLE_USER,
            USER_NAME,
            USER_EMAIL,
            USER_PASS
        );
    }

    @After
    public void tearDown() {
        template.dropCollection(User.class);
        template.dropCollection(Role.class);
    }

}
