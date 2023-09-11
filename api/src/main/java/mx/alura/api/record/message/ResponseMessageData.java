package mx.alura.api.record.message;

import mx.alura.api.model.Message;

import java.sql.Timestamp;

public record ResponseMessageData(
        Long id,
        String message,
        Timestamp creationDate,
        Boolean status,
        Long user,
        Long post
) {

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
