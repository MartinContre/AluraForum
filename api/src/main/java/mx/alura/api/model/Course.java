package mx.alura.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mx.alura.api.record.course.RegisterCourseData;
import mx.alura.api.record.course.UpdateCourseByIdData;
import mx.alura.api.record.course.UpdateCourseData;

@Table(name = "courses")
@Entity(name = "Course")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;

    public Course(Long id) {
        this.id = id;
    }

    public Course(RegisterCourseData registerCourseData) {
        this.name = registerCourseData.name();
        this.category = registerCourseData.category();
    }

    public void updateData(UpdateCourseData updateCourseData) {
        if (updateCourseData.name() != null) {
            this.name = updateCourseData.name();
        }
        if (updateCourseData.category() != null) {
            this.category = updateCourseData.category();
        }
    }

    public void updateData(UpdateCourseByIdData updateCourseByIdData) {
        if (updateCourseByIdData.name() != null) {
            this.name = updateCourseByIdData.name();
        }
        if (updateCourseByIdData.category() != null) {
            this.category = updateCourseByIdData.category();
        }
    }

}
