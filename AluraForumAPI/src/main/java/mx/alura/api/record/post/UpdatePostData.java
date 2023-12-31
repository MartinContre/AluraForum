package mx.alura.api.record.post;

import jakarta.validation.constraints.NotNull;
import mx.alura.api.model.Status;

/**
 * A record representing the data for updating a post.
 */
public record UpdatePostData(
        @NotNull
        Long id,
        String title,
        String message,
        Status status,
        Long userId,
        Long courseId
) {
}
