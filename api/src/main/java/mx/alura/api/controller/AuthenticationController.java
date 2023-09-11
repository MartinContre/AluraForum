package mx.alura.api.controller;

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

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

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
