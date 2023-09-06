package mx.alura.api.domain.topic;

import mx.alura.api.Course;
import mx.alura.api.Status;

import java.sql.Timestamp;

public record TopicResponseData(
        Long id,
        String title,
        String message,
        Timestamp creationDate,
        Status status,
        String authorId,
        Course course
) {
}
