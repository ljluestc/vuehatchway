package macyan.org.english.helper.util;

import java.util.concurrent.ThreadLocalRandom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import static org.springframework.test.context.support.TestPropertySourceUtils.addInlinedPropertiesToEnvironment;

/**
 * @author Yan Matskevich
 * @since 20.04.2021
 */
@Slf4j
public class RandomPortInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final String WIREMOCK_PORT_KEY = "wiremock.server.port";

    public static final int WIREMOCK_PORT_VALUE = ThreadLocalRandom.current().nextInt(10000, 65535);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("Wiremock starts on port {}", WIREMOCK_PORT_VALUE);
        addInlinedPropertiesToEnvironment(applicationContext, WIREMOCK_PORT_KEY + "=" + WIREMOCK_PORT_VALUE);
    }
}
