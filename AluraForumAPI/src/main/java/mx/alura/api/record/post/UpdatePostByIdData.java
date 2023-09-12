package mx.alura.api.record.post;

import mx.alura.api.model.Status;

public record UpdatePostByIdData(
        String title,
        String message,
        Status status,
        Long userId,
        Long courseId
) {
}
