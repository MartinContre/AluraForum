package mx.alura.api.repository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.alura.api.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * This repository interface provides CRUD (Create, Read, Update, Delete) operations for the Message entity.
 */
@Tag(name = "Pots", description = "Operations related to messages")
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Retrieve posts by the username of the user.
     *
     * @param username The username of the user to filter posts.
     * @param pageable The pageable information for pagination.
     * @return A page of messages associated with the specified username.
     */
    @Operation(summary = "List messages by username", description = "Retrieve messages by the username of the user.")

    @Query("""
            SELECT p FROM Post p
            WHERE p.user.username=:username
            ORDER BY p.creationDate DESC
            """)
    Page<Post> findByUsername(
            @Parameter(description = "The username of the user to filter messages.") String username,
            Pageable pageable
    );

    /**
     * Retrieve Post by the name of the course.
     *
     * @param courseName The name of the course to filter posts.
     * @param pageable   The pageable information for pagination.
     * @return A page of messages associated with the specified course name.
     */
    @Operation(summary = "List posts by course name", description = "Retrieve posts by the name of the course.")
    Page<Post> findByCourseName(
            @Parameter(description = "The name of the course to filter messages.") String courseName,
            Pageable pageable
    );
}
