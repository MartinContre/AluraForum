package mx.alura.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import mx.alura.api.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Service for generating and verifying JWT (JSON Web Token) tokens.
 */
@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    /**
     * Generates a JWT token for a given user.
     *
     * @param user The user for whom the token is generated.
     * @return The generated JWT token.
     */
    public String tokenGenerator(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("alura api")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves the subject (username) from a JWT token.
     *
     * @param token The JWT token to verify and extract the subject from.
     * @return The subject (username) extracted from the token.
     */
    public String getSubject(String token) {
        try {
            if (token == null) {
                throw new RuntimeException("Invalid Token");
            }
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("alura api")
                    .build()
                    .verify(token);
            if (verifier.getSubject() == null) {
                throw new RuntimeException("Invalid verifier");
            }
            return verifier.getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Generates the expiration date for a JWT token (2 hours from the current time).
     *
     * @return The expiration date as an Instant.
     */
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }

}
