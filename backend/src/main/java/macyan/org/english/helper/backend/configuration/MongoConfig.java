package macyan.org.english.helper.backend.configuration;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import lombok.AllArgsConstructor;
import macyan.org.english.helper.backend.service.translation.TranslationReadConverter;
import macyan.org.english.helper.backend.service.translation.TranslationWriteConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

/**
 * @author Yan Matskevich
 * @since 06.11.2021
 */
@Configuration
@AllArgsConstructor
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        // TODO: Move to properties
        return "english-helper";
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        // TODO: remove it or use properties
        builder
            .credential(MongoCredential.createCredential("root", "admin", "example".toCharArray()));
    }

    @Override
    protected void configureConverters(MongoCustomConversions.MongoConverterConfigurationAdapter adapter) {
//        adapter.registerConverter(new TranslationReadConverter());
//        adapter.registerConverter(new TranslationWriteConverter());
    }
}
