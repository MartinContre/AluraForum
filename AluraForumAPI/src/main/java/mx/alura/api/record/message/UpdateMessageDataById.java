package mx.alura.api.record.message;

/**
 * A record representing the data for updating a message by its unique identifier.
 */
public record UpdateMessageDataById(
        Long id,
        String message,
        Boolean status,
        Long user,
        Long post
) {
}
