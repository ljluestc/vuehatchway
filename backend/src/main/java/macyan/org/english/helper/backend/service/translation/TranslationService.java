package macyan.org.english.helper.backend.service.translation;

import macyan.org.english.helper.backend.domain.translation.Translation;
import macyan.org.english.helper.backend.domain.translation.Type;

import java.util.List;

public interface TranslationService {
    
    List<Translation> getTranslationsByType(Type type);
    
    Translation saveTranslation(Translation translation);
    
    void deleteTranslation(String id);
    
    Translation updateTranslation(String id, Translation translation);
    
    List<Translation> getAllTranslations();
}
