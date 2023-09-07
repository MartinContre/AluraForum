package mx.alura.api.domain.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record TopicRecordData(
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
}
