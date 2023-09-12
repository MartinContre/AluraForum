package mx.alura.api.record.post;

import mx.alura.api.model.Status;

/**
 * A record representing the data for updating a post by its unique identifier.
 */
public record UpdatePostByIdData(
        String title,
        String message,
        Status status,
        Long userId,
        Long courseId
) {
}
