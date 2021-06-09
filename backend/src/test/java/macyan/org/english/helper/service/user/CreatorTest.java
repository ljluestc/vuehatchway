package macyan.org.english.helper.service.user;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import lombok.SneakyThrows;
import macyan.org.english.helper.controller.request.SignupRequest;
import macyan.org.english.helper.domain.user.Role;
import macyan.org.english.helper.domain.user.RoleRepository;
import macyan.org.english.helper.domain.user.RoleType;
import macyan.org.english.helper.domain.user.User;
import macyan.org.english.helper.domain.user.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class CreatorTest {

    private static final String NAME = "Test";

    private static final String EMAIL = "Test@test.com";

    private static final String PASSWORD = "securityForever";

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private Creator userCreator;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void persistNewUserExceptionCaseUserWithTheSameNameExist() {
        when(userRepository.existsByUsername(NAME))
            .thenReturn(true);

        assertThrows(NotUniqueCredentialsException.class, () -> {
            userCreator.persistNewUser(createRequest());
        });
    }

    @Test
    public void persistNewUserExceptionCaseUserWithTheSameEmailExist() {
        when(userRepository.existsByEmail(EMAIL))
            .thenReturn(true);

        assertThrows(NotUniqueCredentialsException.class, () -> {
            userCreator.persistNewUser(createRequest());
        });
    }

    @SneakyThrows
    @Test
    public void persistNewUser() {
        var userRole = new Role("testId", RoleType.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        when(roleRepository.findByName(RoleType.ROLE_USER))
            .thenReturn(Optional.of(userRole));

        var hash = "passwordHash";
        var user = new User(
            NAME,
            EMAIL,
            hash,
            roles
        );

        when(passwordEncoder.encode(PASSWORD))
            .thenReturn(hash);

        userCreator.persistNewUser(createRequest());

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(argument.capture());
        assertEquals(user, argument.getValue());
    }

    private SignupRequest createRequest() {
        return SignupRequest.builder()
            .username(NAME)
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    }

}