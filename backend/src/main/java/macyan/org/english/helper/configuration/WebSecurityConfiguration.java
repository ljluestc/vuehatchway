package macyan.org.english.helper.configuration;

import lombok.RequiredArgsConstructor;
import macyan.org.english.helper.security.jwt.AuthEntryPointJwt;
import macyan.org.english.helper.security.jwt.AuthTokenFilter;
import macyan.org.english.helper.security.jwt.JwtUtils;
import macyan.org.english.helper.security.service.UserDetailsServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    private final EnglishHelperProperties properties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session will be created or used by spring security
//        .and()
//            .httpBasic()
//        .and()
//            .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/about").permitAll()
//                .antMatchers("api/v1/translation/**").authenticated()
//                .anyRequest().authenticated() // protect all other requests
//        .and()
//            .csrf().disable(); // disable cross site request forgery, as we don't use cookies - otherwise ALL PUT, POST, DELETE will get HTTP 403!

        http
            .cors()
        .and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(authEntryPointJwt())
        .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/test/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("foo").password("{noop}bar").roles("USER");

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils(), userDetailsService);
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils(properties);
    }

    @Bean
    public AuthEntryPointJwt authEntryPointJwt() {
        return new AuthEntryPointJwt();
    }

}
