package mx.alura.api.record.message;

import jakarta.validation.constraints.NotNull;

public record UpdateMessageData(
        @NotNull
        Long id,
        String message,
        Boolean status,
        Long user,
        Long post
) {
}
