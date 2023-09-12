package mx.alura.api.record.user;

import jakarta.validation.constraints.Email;

/**
 * A record representing the data for updating a user by ID.
 */
public record UpdateUserByIdData(
        String name,
        @Email
        String email,
        String password
) {
}
