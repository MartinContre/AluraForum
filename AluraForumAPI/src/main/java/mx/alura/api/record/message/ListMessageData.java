package mx.alura.api.record.message;

import mx.alura.api.model.Message;

import java.sql.Timestamp;

/**
 * A record representing the data for listing a message.
 */
public record ListMessageData(
        Long id,
        String message,
        Timestamp creationDate,
        Boolean status
) {

    /**
     * Constructs a {@code ListMessageData} object from a {@code Message} entity.
     *
     * @param message The message entity to extract data from.
     */
    public ListMessageData(Message message) {
        this(
                message.getId(),
                message.getMessage(),
                message.getCreationDate(),
                message.getStatus()
        );
    }
}
