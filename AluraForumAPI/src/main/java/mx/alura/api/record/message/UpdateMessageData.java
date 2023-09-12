package mx.alura.api.record.message;

import jakarta.validation.constraints.NotNull;

/**
 * A record representing the data for updating a message.
 */
public record UpdateMessageData(
        @NotNull
        Long id,
        String message,
        Boolean status,
        Long user,
        Long post
) {
}
