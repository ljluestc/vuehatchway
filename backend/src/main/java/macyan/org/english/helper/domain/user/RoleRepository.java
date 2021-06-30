package macyan.org.english.helper.domain.user;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Yan Matskevich
 * @since 26.04.2021
 */
public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(RoleType name);

}
