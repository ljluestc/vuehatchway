package macyan.org.english.helper.domain.user;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Yan Matskevich
 * @since 26.04.2021
 */
@Data
@Document(collection = "users")
@Builder()
public class User {

    @Id
    String id;

    @Size(max = 32)
    @NotBlank
    @Indexed
    String username;

    @Size(max = 100)
    @NotBlank
    @Indexed
    String email;

    @NotBlank
    String password;

    @DBRef
    @Singular
    Set<Role> roles;

}
