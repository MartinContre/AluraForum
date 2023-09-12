package mx.alura.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mx.alura.api.infra.security.TokenService;
import mx.alura.api.model.User;
import mx.alura.api.record.authentication.AuthenticationUserData;
import mx.alura.api.record.authentication.JwtTokenData;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling authentication and generating JWT tokens.
 */
@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    /**
     * Constructs a new AuthenticationController.
     *
     * @param authenticationManager The authentication manager.
     * @param tokenService The token service for generating JWT tokens.
     */
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param authenticationUserData The authentication user data.
     * @return ResponseEntity with the JWT token.
     */
    @Operation(summary = "Authenticate User", description = "Authenticates a user and generates a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful, returns JWT token."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication failed.")
    })
    @PostMapping
    public ResponseEntity<JwtTokenData> loginAuthenticated(@RequestBody @Valid AuthenticationUserData authenticationUserData) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                authenticationUserData.username(),
                authenticationUserData.password()
        );

        var authUser = authenticationManager.authenticate(authToken);

        var jwtToken = tokenService.tokenGenerator((User) authUser.getPrincipal());

        return ResponseEntity.ok(new JwtTokenData(jwtToken));
    }

}
