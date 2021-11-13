package macyan.org.english.helper.backend.domain.user;

import java.util.UUID;

import lombok.AllArgsConstructor;
import macyan.org.english.helper.backend.controller.request.SignupRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Yan Matskevich
 * @since 09.06.2021
 */
@AllArgsConstructor
public class Creator {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public void createNewUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new NotUniqueCredentialsException("User with the same name already exists");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new NotUniqueCredentialsException("User with the same email already exists");
        }

        var userRole = roleRepository.findByName(RoleType.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        User user = User.builder()
            .identity(
                UserIdentity.builder()
                    .id(UUID.randomUUID().toString())
                    .build()
            )
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .email(signUpRequest.getEmail())
            .username(signUpRequest.getUsername())
            .role(userRole)
            .build();
        userRepository.save(user);
    }

    /**
     * TODO: to be removed
     */
    public void createAdminUser() {

        var userRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        User user = User.builder()
            .identity(
                UserIdentity.builder()
                    .id(UUID.randomUUID().toString())
                    .build()
            )
            .password(passwordEncoder.encode("12345"))
            .email("test@test.com")
            .username("test")
            .role(userRole)
            .build();
        userRepository.save(user);
    }

}
