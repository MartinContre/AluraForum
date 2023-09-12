package mx.alura.api.record.post;

import mx.alura.api.model.Post;

import java.sql.Timestamp;

/**
 * A record representing the data for listing posts.
 */
public record ListPostData(
        Long id,
        String title,
        String message,
        Timestamp creationDate
) {

    /**
     * Constructs a {@code ListPostData} object from a {@link Post} entity.
     *
     * @param post The {@link Post} entity to extract data from.
     */
    public ListPostData(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getMessage(),
                post.getCreationDate()
        );
    }
}
