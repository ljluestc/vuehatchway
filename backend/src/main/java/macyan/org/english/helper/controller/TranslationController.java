package macyan.org.english.helper.controller;

import java.util.ArrayList;
import java.util.Collection;

import lombok.extern.slf4j.Slf4j;
import macyan.org.english.helper.domain.translation.Translation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for querying user translations.
 *
 * @author Yan Matskevich
 * @since 04.04.2021
 */
@Slf4j
@RestController
@RequestMapping("api/v1/translation")
public class TranslationController {

    @GetMapping(value = "/phrases", produces = "application/json")
    public Collection<Translation> getPhrase() {
        return new ArrayList<>();
    }

    @GetMapping(value = "/phrasal-verbs", produces = "application/json")
    public Collection<Translation> getPhrasalVerbs() {
        return new ArrayList<>();
    }

    @GetMapping(value = "/words", produces = "application/json")
    public Collection<Translation> getWords() {
        return new ArrayList<>();
    }

    @GetMapping(path="/secured")
    @ResponseBody
    /**
     * Test purpose only, delete in the feature
     */
    public String getSecured() {
        log.info("GET successfully called on /secured resource");
        return "Hi from secure controller";
    }
}
