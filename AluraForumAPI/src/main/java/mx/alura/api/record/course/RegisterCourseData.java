package mx.alura.api.record.course;

import jakarta.validation.constraints.NotBlank;

public record RegisterCourseData(
        @NotBlank
        String name,
        @NotBlank
        String category
) {
}
