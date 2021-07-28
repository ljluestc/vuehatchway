package macyan.org.english.helper.backend.controller.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author Yan Matskevich
 * @since 29.04.2021
 */
@Data
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
