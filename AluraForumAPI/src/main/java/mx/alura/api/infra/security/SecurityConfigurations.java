package mx.alura.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up security configurations in the Spring Boot application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    /**
     * Constructs a new SecurityConfigurations instance.
     *
     * @param securityFilter The security filter used for authentication.
     */
    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param httpSecurity The HttpSecurity object to configure.
     * @return A SecurityFilterChain with configured security settings.
     * @throws Exception If there is an error during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(
                        AbstractHttpConfigurer::disable
                )
                .sessionManagement(
                        sessionManagement ->
                                sessionManagement
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        (authorize) ->
                                authorize
                                        .requestMatchers(HttpMethod.POST, "login")
                                        .permitAll()
                                        .requestMatchers("/v3/api-docs/**", "/doc/swagger-ui/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                )
                .exceptionHandling(
                        auth ->
                                auth.authenticationEntryPoint(
                                        ((request, response, authException) -> {
                                            response.setStatus(401);
                                            response.setContentType("application/json");
                                            response.getWriter()
                                                    .write("{" +
                                                            "*******" +
                                                            "Warning: " +
                                                            "You are not authenticated." +
                                                            "*******" +
                                                            "}");
                                        })
                                )
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Creates an AuthenticationManager bean for authentication.
     *
     * @param authenticationConfiguration The AuthenticationConfiguration to use.
     * @return An AuthenticationManager instance.
     * @throws Exception If there is an error creating the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates a PasswordEncoder bean for password hashing.
     *
     * @return A PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
