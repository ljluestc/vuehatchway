package macyan.org.english.helper.configuration;

import lombok.AllArgsConstructor;
import macyan.org.english.helper.domain.user.RoleRepository;
import macyan.org.english.helper.domain.user.UserRepository;
import macyan.org.english.helper.security.service.UserDetailsServiceImpl;
import macyan.org.english.helper.service.user.Creator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Yan Matskevich
 * @since 28.04.2021
 */
@Configuration
@AllArgsConstructor
public class AutoConfig {

    final private UserRepository userRepository;

    final private RoleRepository roleRepository;

    final private PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsServiceImpl UserDetailsServiceImpl() {
        return new UserDetailsServiceImpl(userRepository);
    }

    public Creator creator() {
        return new Creator(userRepository, roleRepository, passwordEncoder);
    }

}
