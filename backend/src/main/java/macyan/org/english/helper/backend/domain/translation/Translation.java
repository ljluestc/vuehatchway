package macyan.org.english.helper.backend.domain.translation;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import macyan.org.english.helper.backend.domain.user.UserIdentity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User translation representation.
 *
 * @author Yan Matskevich
 * @since 04.04.2021
 */
@Data
@Document(collection = "translations")
@Builder()
@CompoundIndexes({
    @CompoundIndex(name = "author_type", def = "{'author' : 1, 'type': -1}"),
    @CompoundIndex(name = "author_tag", def = "{'author' : 1, 'tags': -1}")
})
public class Translation {

    @Id
    final TranslationIdentity identity;

    @Size(max = 255)
    @NotBlank
    final String text;

    @Size(max = 255)
    final String transcription;

    @Size(max = 255)
    @NotBlank
    final String translation;

    @Size(max = 500)
    final String example;

    @DBRef
    @Singular
    final Set<Tag> tags;

    @NotBlank
    final UserIdentity author;

    @NotBlank
    final Type type;

    @NotBlank
    final Date created;

    @NotBlank
    final Date updated;

}
