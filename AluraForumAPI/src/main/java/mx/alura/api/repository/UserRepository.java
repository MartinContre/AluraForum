package mx.alura.api.repository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.alura.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * This repository interface provides CRUD (Create, Read, Update, Delete) operations for the User entity.
 */
@Tag(name = "Users", description = "Operations related to users")
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their username.
     *
     * @param username The username of the user to find.
     * @return UserDetails object representing the user with the given username.
     */
    @Operation(summary = "Find user by username", description = "Find a user by their username.")
    UserDetails findByUsername(
            @Parameter(description = "The username of the user to find.") String username
    );

}
