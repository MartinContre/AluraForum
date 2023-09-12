package mx.alura.api.record.course;

import jakarta.validation.constraints.NotBlank;

/**
 * A record representing data for registering a new course.
 */
public record RegisterCourseData(
        @NotBlank
        String name,
        @NotBlank
        String category
) {
}
