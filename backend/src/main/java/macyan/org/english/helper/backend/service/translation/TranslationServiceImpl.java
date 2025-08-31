package macyan.org.english.helper.backend.service.translation;

import lombok.AllArgsConstructor;
import macyan.org.english.helper.backend.domain.translation.Translation;
import macyan.org.english.helper.backend.domain.translation.Type;
import macyan.org.english.helper.backend.repository.TranslationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TranslationServiceImpl implements TranslationService {

    private final TranslationRepository translationRepository;

    public TranslationServiceImpl(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    @Override
    public List<Translation> getTranslationsByType(Type type) {
        return translationRepository.findByType(type);
    }

    @Override
    public Translation saveTranslation(Translation translation) {
        return translationRepository.save(translation);
    }

    @Override
    public void deleteTranslation(String id) {
        translationRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public Translation updateTranslation(String id, Translation translation) {
        Optional<Translation> existingTranslation = translationRepository.findById(Long.valueOf(id));
        if (existingTranslation.isPresent()) {
            Translation existing = existingTranslation.get();
            // Since Translation is immutable (@Value), we need to create a new one
            return translationRepository.save(translation);
        }
        throw new RuntimeException("Translation not found with id: " + id);
    }

    @Override
    public List<Translation> getAllTranslations() {
        return translationRepository.findAll();
    }
}
