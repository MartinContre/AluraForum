package mx.alura.api.record.course;

import jakarta.validation.constraints.NotNull;

/**
 * A record representing the data for updating a course.
 */
public record UpdateCourseData(
        @NotNull
        Long id,
        String name,
        String category
) {
}
