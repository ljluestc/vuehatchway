package macyan.org.english.helper.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Yan Matskevich
 * @since 28.04.2021
 */
@ConfigurationProperties(prefix = "english-helper")
@Data
public class EnglishHelperProperties {

    private Security security = new Security();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Security {

        private String jwtSecret;

        private int jwtExpirationMs;

        private int jwtRefreshExpirationMs;

    }

}
