package macyan.org.english.helper.backend.security.jwt;

import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import macyan.org.english.helper.backend.configuration.EnglishHelperProperties;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Yan Matskevich
 * @since 28.04.2021
 */
@Slf4j
@AllArgsConstructor
public class JwtUtils {

    private final EnglishHelperProperties properties;

    private final byte[] signingKey;

    public JwtUtils(EnglishHelperProperties properties) {
        this.properties = properties;
        this.signingKey = Base64.getEncoder().encode(properties.getSecurity().getJwtSecret().getBytes());
    }

    public String generateJwtAuthToken(UserDetails userDetail) {
        Key key = new SecretKeySpec(
            signingKey,
            SignatureAlgorithm.HS512.getJcaName()
        );

        return Jwts.builder()
            .setSubject((userDetail.getUsername()))
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + properties.getSecurity().getJwtExpirationMs()))
            .signWith(key)
            .compact();
    }

    public String generateJwtRefreshToken(UserDetails userDetail) {
        Key key = new SecretKeySpec(
            signingKey,
            SignatureAlgorithm.HS512.getJcaName()
        );

        return Jwts.builder()
            .setSubject((userDetail.getUsername()))
            .setIssuedAt(new Date())
            .setExpiration(Date.from(Instant.now().plusMillis(properties.getSecurity().getJwtRefreshExpirationMs())))
            .signWith(key)
            .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
