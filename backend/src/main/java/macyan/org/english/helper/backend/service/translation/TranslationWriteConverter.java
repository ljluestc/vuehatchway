package macyan.org.english.helper.backend.service.translation;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import macyan.org.english.helper.backend.domain.translation.TranslationIdentity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * @author Yan Matskevich
 * @since 06.11.2021
 */
//@WritingConverter
public class TranslationWriteConverter implements Converter<TranslationIdentity, DBObject> {

    @Override
    public DBObject convert(TranslationIdentity source) {
        var dbObject = new BasicDBObject();
        dbObject.put("_idsd", source.getId());
        dbObject.put("test1234", source.getId());
        return dbObject;
    }

}
