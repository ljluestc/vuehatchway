package macyan.org.english.helper.backend.util;

import java.util.Arrays;

import lombok.experimental.UtilityClass;
import macyan.org.english.helper.backend.domain.user.Role;
import macyan.org.english.helper.backend.domain.user.RoleRepository;
import macyan.org.english.helper.backend.domain.user.RoleType;
import macyan.org.english.helper.backend.domain.user.User;
import macyan.org.english.helper.backend.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Yan Matskevich
 * @since 13.06.2021
 */
@UtilityClass
public class UserUtility {

    public void initAllRoles(RoleRepository roleRepository) {
        Arrays.stream(RoleType.values()).forEach(x -> roleRepository.save(new Role(null, x)));
    }

    public void createUser(
        RoleRepository roleRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        RoleType roleType,
        String name,
        String email,
        String password
    ) {
        var userRole = roleRepository.findByName(roleType)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        User user = User.builder()
            .password(passwordEncoder.encode(password))
            .email(email)
            .username(name)
            .role(userRole)
            .build();
        userRepository.save(user);
    }

}
