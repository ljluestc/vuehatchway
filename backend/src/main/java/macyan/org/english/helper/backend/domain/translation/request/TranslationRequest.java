package macyan.org.english.helper.backend.domain.translation.request;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import macyan.org.english.helper.backend.domain.translation.Type;
import org.springframework.lang.Nullable;

/**
 * @author Yan Matskevich
 * @since 06.11.2021
 */
@Value
@Builder
@RequiredArgsConstructor(staticName = "of")
public class TranslationRequest {

    @Nullable
    String id;

    @Size(max = 255)
    @NotBlank
    String text;

    @Size(max = 255)
    @Nullable
    String transcription;

    @Size(max = 255)
    @NotBlank
    String translation;

    @Size(max = 500)
    @NotBlank
    String example;

    @NotBlank
    Type type;

    Set<String> tagIds;
}
