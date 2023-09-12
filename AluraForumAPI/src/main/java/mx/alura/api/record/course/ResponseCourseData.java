package mx.alura.api.record.course;

import mx.alura.api.model.Course;

public record ResponseCourseData(
        Long id,
        String name,
        String category
) {

    public ResponseCourseData(Course course) {
        this(
                course.getId(),
                course.getName(),
                course.getCategory()
        );
    }
}
