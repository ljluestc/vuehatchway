package macyan.org.english.helper.backend.domain.translation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import macyan.org.english.helper.backend.domain.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Translation Tag.
 *
 * @author Yan Matskevich
 * @since 19.08.2021
 */
@Data
@Document(collection = "translations")
@Builder()
public class Tag {

    @Id
    String id;

    @NotBlank
    @Size(max = 255)
    String name;

    @DBRef
    @NotBlank
    User author;

}
