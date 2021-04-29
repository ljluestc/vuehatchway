package macyan.org.english.helper.controller.response;

import java.util.List;

import lombok.Data;

/**
 * @author Yan Matskevich
 * @since 29.04.2021
 */
@Data
public class JwtResponse {

    private String token;

    private String type = "Bearer";

    private String id;

    private String username;

    private String email;

    private List<String> roles;

    public JwtResponse(String accessToken, String id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
