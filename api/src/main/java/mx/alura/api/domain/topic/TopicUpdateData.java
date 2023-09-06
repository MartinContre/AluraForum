package mx.alura.api.domain.topic;

import mx.alura.api.Course;
import mx.alura.api.Status;

public record TopicUpdateData(
        String title,
        String message,
        Status status,
        String authorId,
        Course course
) {
}
