package mx.alura.api.record.course;

import jakarta.validation.constraints.NotNull;

public record UpdateCourseData(
        @NotNull
        Long id,
        String name,
        String category
) {
}
