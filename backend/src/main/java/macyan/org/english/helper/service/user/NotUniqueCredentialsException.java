package macyan.org.english.helper.service.user;

/**
 * @author Yan Matskevich
 * @since 09.06.2021
 */
public class NotUniqueCredentialsException extends Exception {

    public NotUniqueCredentialsException(String message) {
        super(message);
    }

}
