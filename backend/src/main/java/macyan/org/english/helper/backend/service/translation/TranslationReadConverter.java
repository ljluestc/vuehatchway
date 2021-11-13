package macyan.org.english.helper.backend.service.translation;

import com.mongodb.DBObject;
import macyan.org.english.helper.backend.domain.translation.TranslationIdentity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * @author Yan Matskevich
 * @since 06.11.2021
 */
//@ReadingConverter
public class TranslationReadConverter implements Converter<DBObject, TranslationIdentity> {

    @Override
    public TranslationIdentity convert(DBObject source) {
        return TranslationIdentity.builder()
            .id((String) source.get("_id"))
            .build();
    }

}
