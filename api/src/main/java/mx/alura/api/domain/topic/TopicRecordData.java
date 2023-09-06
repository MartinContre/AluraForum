package mx.alura.api.domain.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mx.alura.api.Course;
import mx.alura.api.Status;

import java.sql.Timestamp;

public record TopicRecordData(
        Long id,
        @NotBlank
        String title,
        @NotBlank
        String message,
        Timestamp creationDate,
        @NotNull
        Status status,
        @NotBlank
        String authorId,
        @NotNull
        Course course
) {

        /**
         * This will return specified data.
         *
         * @param topic to be returned.
         */
       public TopicRecordData(Topic topic) {
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
