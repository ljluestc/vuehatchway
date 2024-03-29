package macyan.org.english.helper.backend.domain.translation;

import lombok.Value;

/**
 * User translation representation.
 *
 * @author Yan Matskevich
 * @since 04.04.2021
 */
@Value(staticConstructor = "of")
public class Translation {

    String id;

    String text;

    String transcription;

    String translation;

}
