package macyan.org.english.helper.backend.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import macyan.org.english.helper.backend.domain.translation.Translation;
import macyan.org.english.helper.backend.domain.translation.Type;
import macyan.org.english.helper.backend.repository.TranslationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final TranslationRepository translationRepository;

    public DataInitializer(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Clear existing data
        translationRepository.deleteAll();
        
        // Create sample translations
        List<Translation> translations = Arrays.asList(
            // Phrases
            new Translation(1L, "How are you?", "haʊ ɑː juː", "Как дела?", Type.PHRASE),
            new Translation(2L, "Nice to meet you", "naɪs tuː miːt juː", "Приятно познакомиться", Type.PHRASE),
            new Translation(3L, "What's your name?", "wɒts jɔː neɪm", "Как тебя зовут?", Type.PHRASE),
            
            // Words
            new Translation(4L, "Hello", "həˈləʊ", "Привет", Type.WORD),
            new Translation(5L, "Goodbye", "ˌɡʊdˈbaɪ", "До свидания", Type.WORD),
            new Translation(6L, "Thank you", "ˈθæŋk juː", "Спасибо", Type.WORD),
            
            // Phrasal verbs
            new Translation(7L, "Look up", "lʊk ʌp", "Искать", Type.PHRASAL_VERB),
            new Translation(8L, "Give up", "ɡɪv ʌp", "Сдаваться", Type.PHRASAL_VERB),
            new Translation(9L, "Get up", "ɡet ʌp", "Вставать", Type.PHRASAL_VERB)
        );
        
        translationRepository.saveAll(translations);
        log.info("Initialized {} translations", translations.size());
    }
}
