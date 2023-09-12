package mx.alura.api.record.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * A record representing the data for registering a message.
 */
public record RegisterMessageData(
        @NotBlank
        String message,
        @NotNull
        Long user,
        @NotNull
        Long post
) {
}
