package macyan.org.english.helper.security.service;

import lombok.AllArgsConstructor;
import macyan.org.english.helper.domain.user.User;
import macyan.org.english.helper.domain.user.UserRepository;
import macyan.org.english.helper.security.UserDetailsImpl;
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
