package mx.alura.api.record.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * A record representing the data for updating a user.
 */
public record UpdateUserData(
        @NotNull
        Long id,
        String name,
        @Email
        String email,
        String password
) {
}
