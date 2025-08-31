package macyan.org.english.helper.backend.repository;

import macyan.org.english.helper.backend.domain.translation.Translation;
import macyan.org.english.helper.backend.domain.translation.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    
    List<Translation> findByType(Type type);
    
    List<Translation> findByTextContainingIgnoreCase(String text);
}
