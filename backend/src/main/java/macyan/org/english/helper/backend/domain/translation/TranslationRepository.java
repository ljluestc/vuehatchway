package macyan.org.english.helper.backend.domain.translation;

import java.util.Optional;

import macyan.org.english.helper.backend.domain.user.UserIdentity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Yan Matskevich
 * @since 19.08.2021
 */
public interface TranslationRepository extends MongoRepository<Translation, TranslationIdentity> {

    Optional<Translation> findByAuthorAndType(UserIdentity user, Type type);

    Optional<Translation> findByAuthor(UserIdentity user);

    Optional<Translation> findByIdentity(TranslationIdentity translationIdentity);

}
