package macyan.org.english.helper.service.user;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import macyan.org.english.helper.controller.request.SignupRequest;
import macyan.org.english.helper.domain.user.Role;
import macyan.org.english.helper.domain.user.RoleRepository;
import macyan.org.english.helper.domain.user.RoleType;
import macyan.org.english.helper.domain.user.User;
import macyan.org.english.helper.domain.user.UserRepository;
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

    public void persistNewUser(SignupRequest signUpRequest) throws NotUniqueCredentialsException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new NotUniqueCredentialsException("User with the same name already exists");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new NotUniqueCredentialsException("User with the same email already exists");
        }

        var userRole = roleRepository.findByName(RoleType.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = new User(
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            passwordEncoder.encode(signUpRequest.getPassword()),
            roles
        );
        userRepository.save(user);
    }

}
