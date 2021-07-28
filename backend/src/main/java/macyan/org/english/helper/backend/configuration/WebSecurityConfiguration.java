package macyan.org.english.helper.backend.configuration;

import lombok.RequiredArgsConstructor;
import macyan.org.english.helper.backend.domain.user.RoleRepository;
import macyan.org.english.helper.backend.domain.user.UserRepository;
import macyan.org.english.helper.backend.security.jwt.AuthEntryPointJwt;
import macyan.org.english.helper.backend.security.jwt.AuthTokenFilter;
import macyan.org.english.helper.backend.security.jwt.JwtUtils;
import macyan.org.english.helper.backend.security.service.UserDetailsServiceImpl;
import macyan.org.english.helper.backend.service.user.Creator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Yan Matskevich
 * @since 20.04.2021
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(EnglishHelperProperties.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final EnglishHelperProperties properties;

    final private RoleRepository roleRepository;

    final private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
        .and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(authEntryPointJwt())
        .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/api/auth/signup").hasRole("ADMIN")
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/translation/**").authenticated()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils(), userDetailsServiceImpl());
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils(properties);
    }

    @Bean
    public AuthEntryPointJwt authEntryPointJwt() {
        return new AuthEntryPointJwt();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public Creator creator() {
        return new Creator(userRepository, roleRepository, passwordEncoder());
    }

    @Bean
    public UserDetailsServiceImpl userDetailsServiceImpl() {
        return new UserDetailsServiceImpl(userRepository);
    }

}
