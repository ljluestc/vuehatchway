package macyan.org.english.helper.controller.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

/**
 * @author Yan Matskevich
 * @since 29.04.2021
 */
@Data
@Builder
public class SignupRequest {

    @NotBlank
    @Size(min = 2, max = 32)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
}
