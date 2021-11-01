package macyan.org.english.helper.backend.controller;

import java.util.ArrayList;
import java.util.Collection;

import lombok.extern.slf4j.Slf4j;
import macyan.org.english.helper.backend.domain.translation.Translation;
import macyan.org.english.helper.backend.domain.translation.Type;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for querying user translations.
 *
 * @author Yan Matskevich
 * @since 04.04.2021
 */
@Slf4j
@RestController
@RequestMapping("api/translations")
public class TranslationController {

    // оидн контроллер с гет параметро на тип. Перемапливать userDetails из контекста и делать запрос в репозиторий
    // добавить проверку на валидность переданного типа
    // Использовать pageRequests https://stackoverflow.com/questions/52975758/provide-limit-on-spring-data-mongo-repository

    @GetMapping(value = "/", produces = "application/json")
    public Collection<Translation> get(@RequestParam(required = false) Type type) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getPrincipal();



        return new ArrayList<>();
    }

    @GetMapping(value = "/phrases", produces = "application/json")
    public Collection<Translation> getPhrase() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getPrincipal();



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
}
