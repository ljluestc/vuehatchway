package macyan.org.english.helper.domain.translation;

import lombok.Builder;
import lombok.Value;

/**
 * User translation representation.
 *
 * @author Yan Matskevich
 * @since 04.04.2021
 */
@Value(staticConstructor = "of")
@Builder(toBuilder = true)
public class Translation {
    String id;
    String text;
    String transcription;
    String translation;
}
