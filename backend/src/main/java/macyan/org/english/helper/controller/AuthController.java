package macyan.org.english.helper.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import macyan.org.english.helper.configuration.EnglishHelperProperties;
import macyan.org.english.helper.controller.request.LoginRequest;
import macyan.org.english.helper.controller.request.SignupRequest;
import macyan.org.english.helper.controller.response.JwtResponse;
import macyan.org.english.helper.controller.response.MessageResponse;
import macyan.org.english.helper.security.UserDetailsImpl;
import macyan.org.english.helper.security.jwt.JwtUtils;
import macyan.org.english.helper.service.user.Creator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yan Matskevich
 * @since 29.04.2021
 */
@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final EnglishHelperProperties properties;

    private final UserDetailsService userDetailsService;

    private final Creator userCreator;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        setRefreshJwtCookie(response, userDetails);
        return  ResponseEntity.ok(createJwtResponse(userDetails));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshJwt = getRefreshTokenFromRequest(request);

        if (null == refreshJwt) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (jwtUtils.validateJwtToken(refreshJwt)) {
            String username = jwtUtils.getUserNameFromJwtToken(refreshJwt);
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
            setRefreshJwtCookie(response, userDetails);
            return ResponseEntity.ok(createJwtResponse(userDetails));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            userCreator.persistNewUser(signUpRequest);
        } catch (Exception e) {
            log.error("Can not create new User: {}", e.getMessage());
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: " + e.getMessage()));
        }

        log.info("Created new user: email " + signUpRequest.getEmail());
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private void setRefreshJwtCookie(HttpServletResponse response, UserDetails userDetails) {
        String newRefreshJwt = jwtUtils.generateJwtRefreshToken(userDetails);
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, newRefreshJwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(properties.getSecurity().getJwtRefreshExpirationMs());
        response.addCookie(cookie);
    }

    private JwtResponse createJwtResponse(UserDetailsImpl userDetails) {
        String jwt = jwtUtils.generateJwtAuthToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return new JwtResponse(
            jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles
        );
    }

    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        var cookies = request.getCookies();
        var jwtOptional = Arrays.stream(cookies).filter(cookie -> REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName()))
            .findFirst();
        return jwtOptional.isEmpty() ? null : jwtOptional.get().getValue();
    }
}
