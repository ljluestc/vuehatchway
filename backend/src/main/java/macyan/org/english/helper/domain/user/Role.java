package macyan.org.english.helper.domain.user;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Yan Matskevich
 * @since 26.04.2021
 */
@Document(collection = "roles")
@Value
public class Role {

    @Id
    String id;

    RoleType name;

}
