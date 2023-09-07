package mx.alura.api.domain.topic;

public record TopicUpdateData(
        String title,
        String message,
        Status status,
        String authorId,
        Course course
) {
}
