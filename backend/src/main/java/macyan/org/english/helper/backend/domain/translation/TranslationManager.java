package macyan.org.english.helper.backend.domain.translation;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import macyan.org.english.helper.backend.domain.translation.request.TranslationRequest;
import macyan.org.english.helper.backend.domain.user.UserIdentity;
import org.springframework.stereotype.Service;

/**
 * @author Yan Matskevich
 * @since 06.11.2021
 */
@Service
@AllArgsConstructor
public class TranslationManager {

    private final TranslationRepository translationRepository;

    public void handleTranslationRequest(TranslationRequest translationRequest, UserIdentity userIdentity) {
        if (translationRequest.getId() == null) {
            createTranslation(translationRequest, userIdentity);
            return;
        }

        updateTranslation(translationRequest, userIdentity);
    }

    public void createTranslation(TranslationRequest translationRequest, UserIdentity userIdentity) {
        var translation = Translation.builder()
            .identity(
                TranslationIdentity.builder()
                    .id(UUID.randomUUID().toString())
                    .build()
            )
            .author(userIdentity)
            .translation("test")
            .type(Type.PHRASE)
            .text("test")
            .created(new Date())
            .updated(new Date())
            .build();

        translationRepository.save(translation);
    }

    private void updateTranslation(
        TranslationRequest translationRequest,
        UserIdentity userIdentity
    ) {
        // TBD;
    }

}
