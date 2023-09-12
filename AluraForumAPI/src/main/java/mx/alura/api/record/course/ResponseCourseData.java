package mx.alura.api.record.course;

import mx.alura.api.model.Course;

/**
 * A record representing the response data for a course.
 */
public record ResponseCourseData(
        Long id,
        String name,
        String category
) {

    /**
     * Constructs a new instance of ResponseCourseData based on a Course entity.
     *
     * @param course The Course entity to extract data from.
     */
    public ResponseCourseData(Course course) {
        this(
                course.getId(),
                course.getName(),
                course.getCategory()
        );
    }
}
