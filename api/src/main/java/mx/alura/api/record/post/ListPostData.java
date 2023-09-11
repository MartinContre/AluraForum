package mx.alura.api.record.post;

import mx.alura.api.model.Post;

import java.sql.Timestamp;

public record ListPostData(
        Long id,
        String title,
        String message,
        Timestamp creationDate
) {
    public ListPostData(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getMessage(),
                post.getCreationDate()
        );
    }
}
