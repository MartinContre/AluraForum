package mx.alura.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

/**
 * Controller responsible for managing courses in the forum.
 */
@RestController
@RequestMapping("/forum/courses")
@SecurityRequirement(name = "bearerAuth")
public class CourseController {

    private final CourseRepository courseRepository;

    /**
     * Constructs a new CourseController.
     *
     * @param courseRepository The repository for managing courses.
     */
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Registers a new course.
     *
     * @param registerCourseData     The data to register a new course.
     * @param uriComponentsBuilder   The URI components builder for generating response URI.
     * @return ResponseEntity with the registered course data.
     */
    @Operation(summary = "Register a new course", description = "Registers a new course.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course registered successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required.")
    })
    @PostMapping
    public ResponseEntity<ResponseCourseData> registerCourse(
            @RequestBody @Valid RegisterCourseData registerCourseData,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Course course = courseRepository.save(new Course(registerCourseData));
        URI uri = uriComponentsBuilder.path("/forum/course/{id}").buildAndExpand(course.getId()).toUri();

        return ResponseEntity.created(uri).body(new ResponseCourseData(course));
    }

    /**
     * Lists all courses.
     *
     * @param pageable The pageable request for pagination.
     * @return ResponseEntity with a page of course data.
     */
    @Operation(summary = "List courses", description = "Lists all courses.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courses listed successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required.")
    })
    @GetMapping
    public ResponseEntity<Page<ResponseCourseData>> listCourses(
            @PageableDefault(size = 5, sort = "name")
            Pageable pageable
    ) {
        return ResponseEntity.ok(courseRepository.findAll(pageable).map(ResponseCourseData::new));
    }

    /**
     * Retrieves a course by its ID.
     *
     * @param id The ID of the course to retrieve.
     * @return ResponseEntity with the course data if found, or an error response if not found.
     */
    @Operation(summary = "Get course by ID", description = "Retrieves a course by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "Course not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCourseData> getCourseById(@PathVariable Long id) {
        Course course = courseRepository.getReferenceById(id);
        return ResponseEntity.ok(new ResponseCourseData(course));
    }

    /**
     * Updates a course's data.
     *
     * @param updateCourseData The data for updating the course.
     * @return ResponseEntity with the updated course data if successful, or an error response if not found or the request is invalid.
     */
    @Operation(summary = "Update a course", description = "Updates a course's data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "Course not found.")
    })
    @PutMapping
    @Transactional
    public ResponseEntity<ResponseCourseData> updateCourse(@RequestBody @Valid UpdateCourseData updateCourseData) {
        Course course = courseRepository.getReferenceById(updateCourseData.id());
        course.updateData(updateCourseData);
        return ResponseEntity.ok(new ResponseCourseData(course));
    }

    /**
     * Updates a course's data by its ID.
     *
     * @param updateCourseByIdData The data for updating the course.
     * @param id                   The ID of the course to update.
     * @return ResponseEntity with the updated course data if successful, or an error response if not found or the request is invalid.
     */
    @Operation(summary = "Update a course by ID", description = "Updates a course's data by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "Course not found.")
    })
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

    /**
     * Deletes a course by its ID.
     *
     * @param id The ID of the course to delete.
     * @return ResponseEntity with a success message if deleted, or an error response if not found.
     */
    @Operation(summary = "Delete a course", description = "Deletes a course by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "Course not found.")
    })
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
