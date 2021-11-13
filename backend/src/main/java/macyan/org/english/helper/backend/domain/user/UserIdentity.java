package macyan.org.english.helper.backend.domain.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author Yan Matskevich
 * @since 13.11.2021
 */
@Builder
@Data
public class UserIdentity {

    @Id
    final String id;

}
