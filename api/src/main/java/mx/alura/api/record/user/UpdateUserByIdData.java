package mx.alura.api.record.user;

import jakarta.validation.constraints.Email;

public record UpdateUserByIdData(
        String name,
        @Email
        String email,
        String password
) {
}
