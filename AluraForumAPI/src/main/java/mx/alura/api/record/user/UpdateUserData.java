package mx.alura.api.record.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UpdateUserData(
        @NotNull
        Long id,
        String name,
        @Email
        String email,
        String password
) {
}
