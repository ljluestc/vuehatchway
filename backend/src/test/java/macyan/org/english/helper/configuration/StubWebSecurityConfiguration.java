//package macyan.org.english.helper.configuration;
//
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * @author Yan Matskevich
// * @since 20.04.2021
// */
//@Configuration
//@EnableAutoConfiguration
//@Order(value = 10)
//public class StubWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser("foo").password("{noop}bar").roles("USER");
//    }
//}
