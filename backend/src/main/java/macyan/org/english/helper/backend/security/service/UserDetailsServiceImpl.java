package macyan.org.english.helper.backend.security.service;

import lombok.AllArgsConstructor;
import macyan.org.english.helper.backend.domain.user.User;
import macyan.org.english.helper.backend.security.UserDetailsImpl;
import macyan.org.english.helper.backend.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Yan Matskevich
 * @since 28.04.2021
 */
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found by username: " + username));

        return UserDetailsImpl.build(user);
    }

}
