package mx.alura.api.record.post;

import mx.alura.api.model.Post;
import mx.alura.api.model.Status;

import java.sql.Timestamp;

/**
 * A record representing the response data for a post.
 */
public record ResponsePostData(
        Long id,
        String title,
        String message,
        Timestamp creationDate,
        Status status,
        Long userId,
        Long courseId
) {

    /**
     * Constructs a ResponsePostData object based on a Post entity.
     *
     * @param post The Post entity from which to create the response data.
     */
    public ResponsePostData(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getMessage(),
                post.getCreationDate(),
                post.getStatus(),
                post.getUser().getId(),
                post.getCourse().getId()
        );
    }
}
