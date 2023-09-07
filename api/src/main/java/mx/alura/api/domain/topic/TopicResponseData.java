package mx.alura.api.domain.topic;

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

    public TopicResponseData(Topic topic) {
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationDate(),
                topic.getStatus(),
                topic.getAuthorId(),
                topic.getCourse()
        );
    }
}
