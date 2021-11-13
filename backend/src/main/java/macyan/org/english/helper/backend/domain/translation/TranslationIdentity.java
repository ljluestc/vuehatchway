package macyan.org.english.helper.backend.domain.translation;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author Yan Matskevich
 * @since 06.11.2021
 */
@Data
@Builder
public class TranslationIdentity {

    @Id
    final String id;

}
