package mx.alura.api.record.message;

import mx.alura.api.model.Message;

import java.sql.Timestamp;

public record ListMessageData(
        Long id,
        String message,
        Timestamp creationDate,
        Boolean status
) {

    public ListMessageData(Message message) {
        this(
                message.getId(),
                message.getMessage(),
                message.getCreationDate(),
                message.getStatus()
        );
    }
}
