package mx.alura.api.record.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mx.alura.api.model.Status;


/**
 * A record representing the data for registering a post.
 */
public record RegisterPostData(
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotNull
        Status status,
        @NotNull
        Long userId,
        @NotNull
        Long courseId
) {
}
