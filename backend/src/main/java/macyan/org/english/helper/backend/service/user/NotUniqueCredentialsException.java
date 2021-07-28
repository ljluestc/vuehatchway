package macyan.org.english.helper.backend.service.user;

/**
 * @author Yan Matskevich
 * @since 09.06.2021
 */
public class NotUniqueCredentialsException extends RuntimeException {

    public NotUniqueCredentialsException(String message) {
        super(message);
    }

}
