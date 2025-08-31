package macyan.org.english.helper.backend.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import macyan.org.english.helper.backend.domain.translation.Translation;
import macyan.org.english.helper.backend.domain.translation.Type;
import macyan.org.english.helper.backend.service.translation.TranslationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for querying user translations.
 *
 * @author Yan Matskevich
 * @since 04.04.2021
 */
@Slf4j
@RestController
@RequestMapping("api/translation")
@CrossOrigin(origins = "*")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping(value = "/phrases", produces = "application/json")
    public ResponseEntity<List<Translation>> getPhrases() {
        List<Translation> phrases = translationService.getTranslationsByType(Type.PHRASE);
        return ResponseEntity.ok(phrases);
    }

    @GetMapping(value = "/phrasal-verbs", produces = "application/json")
    public ResponseEntity<List<Translation>> getPhrasalVerbs() {
        List<Translation> phrasalVerbs = translationService.getTranslationsByType(Type.PHRASAL_VERB);
        return ResponseEntity.ok(phrasalVerbs);
    }

    @GetMapping(value = "/words", produces = "application/json")
    public ResponseEntity<List<Translation>> getWords() {
        List<Translation> words = translationService.getTranslationsByType(Type.WORD);
        return ResponseEntity.ok(words);
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<Translation>> getAllTranslations() {
        List<Translation> allTranslations = translationService.getAllTranslations();
        return ResponseEntity.ok(allTranslations);
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Translation> createTranslation(@RequestBody Translation translation) {
        Translation savedTranslation = translationService.saveTranslation(translation);
        return ResponseEntity.ok(savedTranslation);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Translation> updateTranslation(@PathVariable String id, @RequestBody Translation translation) {
        Translation updatedTranslation = translationService.updateTranslation(id, translation);
        return ResponseEntity.ok(updatedTranslation);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTranslation(@PathVariable String id) {
        translationService.deleteTranslation(id);
        return ResponseEntity.ok().build();
    }
}
