package mx.alura.api.repository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.alura.api.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * This repository interface provides CRUD (Create, Read, Update, Delete) operations for the Message entity.
 */
@Tag(name = "Messages", description = "Operations related to messages")

public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Retrieve messages by the name of the course.
     *
     * @param courseName The name of the course to filter messages.
     * @param pageable   The pageable information for pagination.
     * @return A page of messages associated with the specified course name.
     */
    @Operation(summary = "List messages by course name", description = "Retrieve messages by the name of the course.")
    @Query("""
                SELECT m FROM Message m WHERE m.postId.course.name=:courseName
            """)
    Page<Message> findByCourseName(
            @Parameter(description = "The name of the course to filter messages.") String courseName,
            Pageable pageable
    );

    /**
     * Retrieve messages by the username of the user.
     *
     * @param username The username of the user to filter messages.
     * @param pageable The pageable information for pagination.
     * @return A page of messages associated with the specified username.
     */
    @Operation(summary = "List messages by username", description = "Retrieve messages by the username of the user.")
    @Query("""
                SELECT m FROM Message m WHERE m.userId.username=:username
            """)
    Page<Message> findByUsername(
            @Parameter(description = "The username of the user to filter messages.") String username,
            Pageable pageable
    );
}
