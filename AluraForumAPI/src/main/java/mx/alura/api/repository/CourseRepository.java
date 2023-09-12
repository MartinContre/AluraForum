package mx.alura.api.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import mx.alura.api.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository interface provides CRUD (Create, Read, Update, Delete) operations for the Course entity.
 */
@Tag(name = "Courses", description = "Operations related to courses")
public interface CourseRepository extends JpaRepository<Course, Long> {
}
