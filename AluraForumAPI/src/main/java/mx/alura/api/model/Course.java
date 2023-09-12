package mx.alura.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mx.alura.api.record.course.RegisterCourseData;
import mx.alura.api.record.course.UpdateCourseByIdData;
import mx.alura.api.record.course.UpdateCourseData;

/**
 * Represents a Course entity in the application.
 */
@Table(name = "courses")
@Entity(name = "Course")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {

    @Schema(description = "Course ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Name of the course")
    private String name;

    @Schema(description = "Category of the course")
    private String category;

    /**
     * Constructs a new Course object with the specified ID.
     *
     * @param id The course ID.
     */
    public Course(Long id) {
        this.id = id;
    }

    /**
     * Constructs a new Course instance using data from a RegisterCourseData object.
     *
     * @param registerCourseData The data used to initialize the Course.
     */
    public Course(RegisterCourseData registerCourseData) {
        this.name = registerCourseData.name();
        this.category = registerCourseData.category();
    }

    /**
     * Updates the Course data based on the provided UpdateCourseData object.
     *
     * @param updateCourseData The data used to update the Course.
     */
    public void updateData(UpdateCourseData updateCourseData) {
        if (updateCourseData.name() != null) {
            this.name = updateCourseData.name();
        }
        if (updateCourseData.category() != null) {
            this.category = updateCourseData.category();
        }
    }

    /**
     * Updates the Course data based on the provided UpdateCourseByIdData object.
     *
     * @param updateCourseByIdData The data used to update the Course by its ID.
     */
    public void updateData(UpdateCourseByIdData updateCourseByIdData) {
        if (updateCourseByIdData.name() != null) {
            this.name = updateCourseByIdData.name();
        }
        if (updateCourseByIdData.category() != null) {
            this.category = updateCourseByIdData.category();
        }
    }

}
