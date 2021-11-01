package macyan.org.english.helper.backend.domain.translation;

import java.util.Optional;

import macyan.org.english.helper.backend.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Yan Matskevich
 * @since 19.08.2021
 */
public interface TranslationRepository extends MongoRepository<Translation, String> {

    Optional<Translation> findByAuthorAndType(User user, Type type);

    Optional<Translation> findByAuthor(User user);

}
