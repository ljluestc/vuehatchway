package macyan.org.english.helper.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import macyan.org.english.helper.configuration.EnglishHelperProperties;
import macyan.org.english.helper.controller.request.LoginRequest;
import macyan.org.english.helper.controller.request.SignupRequest;
import macyan.org.english.helper.controller.response.JwtResponse;
import macyan.org.english.helper.controller.response.MessageResponse;
import macyan.org.english.helper.domain.user.Role;
import macyan.org.english.helper.domain.user.RoleRepository;
import macyan.org.english.helper.domain.user.RoleType;
import macyan.org.english.helper.domain.user.User;
import macyan.org.english.helper.domain.user.UserRepository;
import macyan.org.english.helper.security.UserDetailsImpl;
import macyan.org.english.helper.security.jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yan Matskevich
 * @since 29.04.2021
 */
@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final EnglishHelperProperties properties;

    private final UserDetailsService userDetailsService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        setRefreshJwtCookie(response, authentication);
        return  ResponseEntity.ok(createJwtResponse(userDetails, authentication));
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
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword()));

            setRefreshJwtCookie(response, authentication);
            return ResponseEntity.ok(createJwtResponse(userDetails, authentication));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email is already in use!"));
        }

        // @todo Move out from controller after writing tests
        // Create new user's account
        var userRole = roleRepository.findByName(RoleType.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        User user = new User(
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            passwordEncoder.encode(signUpRequest.getPassword()),
            roles
        );

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private void setRefreshJwtCookie(HttpServletResponse response, Authentication authentication) {
        String newRefreshJwt = jwtUtils.generateJwtRefreshToken(authentication);
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, newRefreshJwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(properties.getSecurity().getJwtRefreshExpirationMs());
        response.addCookie(cookie);
    }

    private JwtResponse createJwtResponse(UserDetailsImpl userDetails, Authentication authentication) {
        String jwt = jwtUtils.generateJwtAuthToken(authentication);

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

    /**
     * todo: move to service.
     */
    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        var cookies = request.getCookies();
        var jwtOptional = Arrays.stream(cookies).filter(cookie -> REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName()))
            .findFirst();
        return jwtOptional.isEmpty() ? null : jwtOptional.get().getValue();
    }
}
