package macyan.org.english.helper.configuration;

import lombok.AllArgsConstructor;
import macyan.org.english.helper.domain.user.UserRepository;
import macyan.org.english.helper.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yan Matskevich
 * @since 28.04.2021
 */
@Configuration
@AllArgsConstructor
public class AutoConfig {

    final private UserRepository userRepository;

    @Bean
    public UserDetailsServiceImpl UserDetailsServiceImpl() {
        return new UserDetailsServiceImpl(userRepository);
    }

}
