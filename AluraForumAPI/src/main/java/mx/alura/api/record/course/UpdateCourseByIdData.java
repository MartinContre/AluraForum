package mx.alura.api.record.course;

/**
 * A record representing the data for updating a course by its unique identifier.
 */
public record UpdateCourseByIdData(
        String name,
        String category
) {
}
