package mx.alura.api.record.message;

public record UpdateMessageDataById(
        Long id,
        String message,
        Boolean status,
        Long user,
        Long post
) {
}
