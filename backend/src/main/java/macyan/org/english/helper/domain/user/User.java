package macyan.org.english.helper.domain.user;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Yan Matskevich
 * @since 26.04.2021
 */
@Data
@Document(collection = "users")
public class User {

    @Id
    String id;

    @Size(max = 32)
    @NotBlank
    String username;

    @Size(max = 100)
    @Email
    @NotBlank
    String email;

    @NotBlank
    String password;

    @DBRef
    Set<Role> roles;

    public User(String username, String email, String password, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

}
