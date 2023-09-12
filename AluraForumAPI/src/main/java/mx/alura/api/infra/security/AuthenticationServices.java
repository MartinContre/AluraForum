package mx.alura.api.infra.security;

import mx.alura.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service responsible for authenticating users by implementing the UserDetailsService interface.
 */
@Service
public class AuthenticationServices implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a new AuthenticationServices instance.
     *
     * @param userRepository The repository for managing user data.
     */
    public AuthenticationServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Load a user by their username for authentication.
     *
     * @param username The username of the user to load.
     * @return UserDetails representing the loaded user if found.
     * @throws UsernameNotFoundException If the user with the given username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
