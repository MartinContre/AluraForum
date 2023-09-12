package mx.alura.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mx.alura.api.infra.errors.ErrorHandler;
import mx.alura.api.model.Course;
import mx.alura.api.record.course.RegisterCourseData;
import mx.alura.api.record.course.ResponseCourseData;
import mx.alura.api.record.course.UpdateCourseByIdData;
import mx.alura.api.record.course.UpdateCourseData;
import mx.alura.api.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/forum/courses")
@SecurityRequirement(name = "bearerAuth")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostMapping
    public ResponseEntity<ResponseCourseData> registerCourse(
            @RequestBody @Valid RegisterCourseData registerCourseData,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Course course = courseRepository.save(new Course(registerCourseData));
        URI uri = uriComponentsBuilder.path("/forum/course/{id}").buildAndExpand(course.getId()).toUri();

        return ResponseEntity.created(uri).body(new ResponseCourseData(course));
    }

    @GetMapping
    public ResponseEntity<Page<ResponseCourseData>> listCourses(
            @PageableDefault(size = 5, sort = "name")
            Pageable pageable
    ) {
        return ResponseEntity.ok(courseRepository.findAll(pageable).map(ResponseCourseData::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCourseData> getCourseById(@PathVariable Long id) {
        Course course = courseRepository.getReferenceById(id);
        return ResponseEntity.ok(new ResponseCourseData(course));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ResponseCourseData> updateCourse(@RequestBody @Valid UpdateCourseData updateCourseData) {
        Course course = courseRepository.getReferenceById(updateCourseData.id());
        course.updateData(updateCourseData);
        return ResponseEntity.ok(new ResponseCourseData(course));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseCourseData> updateCourseById(
            @RequestBody @Valid UpdateCourseByIdData updateCourseByIdData,
            @PathVariable Long id
    ) {
        Course course = courseRepository.getReferenceById(id);
        course.updateData(updateCourseByIdData);
        return ResponseEntity.ok(new ResponseCourseData(course));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        if (courseRepository.existsById(id)) {
            Course course = courseRepository.getReferenceById(id);
            courseRepository.delete(course);
            return ResponseEntity.noContent().build();
        }
        return new ErrorHandler().handlerError404();
    }

}
