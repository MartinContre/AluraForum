package mx.alura.api.record.message;

import mx.alura.api.model.Message;

import java.sql.Timestamp;

/**
 * A record representing the response data for a message.
 */
public record ResponseMessageData(
        Long id,
        String message,
        Timestamp creationDate,
        Boolean status,
        Long user,
        Long post
) {

    /**
     * Creates a new instance of {@code ResponseMessageData} based on a {@link Message} entity.
     *
     * @param message The {@link Message} entity to extract data from.
     */
    public ResponseMessageData(Message message) {
        this(
                message.getId(),
                message.getMessage(),
                message.getCreationDate(),
                message.getStatus(),
                message.getUserId().getId(),
                message.getPostId().getId()
        );
    }
}
