package macyan.org.english.helper.backend.domain.translation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * User translation representation.
 *
 * @author Yan Matskevich
 * @since 04.04.2021
 */
@Entity
@Table(name = "translations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String transcription;

    @Column(nullable = false)
    private String translation;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;
}
