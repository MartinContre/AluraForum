package mx.alura.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mx.alura.api.record.message.RegisterMessageData;
import mx.alura.api.record.message.UpdateMessageData;
import mx.alura.api.record.message.UpdateMessageDataById;
import mx.alura.api.utils.TimestampUtility;

import java.sql.Timestamp;

/**
 * Represents a message (Response) in the system.
 */
@Table(name = "messages")
@Entity(name = "Message")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Message {

    @Schema(description = "Message ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "The content of the message.")
    private String message;

    @Schema(description = "The creation date of the message.")
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Schema(description = "The status of the message (true for active," +
            "false for inactive).")
    private Boolean status = true;

    @Schema(description = "The user id who posted the message.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @Schema(description = "The post id to which this message belongs.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post postId;

    /**
     * Creates a new message with the provided data.
     *
     * @param registerMessageData The data to register the message.
     */
    public Message(RegisterMessageData registerMessageData) {
        this.message = registerMessageData.message();
        this.creationDate = TimestampUtility.getTimeNowRounded();
        this.userId = new User(registerMessageData.user());
        this.postId = new Post(registerMessageData.post());
    }

    /**
     * Updates the message data with the provided data.
     *
     * @param updateMessageData The data to update the message.
     */
    public void updateData(UpdateMessageData updateMessageData) {
        if (updateMessageData.message() != null) {
            this.message = updateMessageData.message();
        }
        if (updateMessageData.status() != null) {
            this.status = updateMessageData.status();
        }
        if (updateMessageData.user() != null) {
            this.userId = new User(updateMessageData.user());
        }
        if (updateMessageData.post() != null) {
            this.postId = new Post(updateMessageData.post());
        }
    }

    /**
     * Updates the message data with the provided data.
     *
     * @param updateMessageDataById The data to update the message.
     */
    public void updateData(UpdateMessageDataById updateMessageDataById) {
        if (updateMessageDataById.message() != null) {
            this.message = updateMessageDataById.message();
        }
        if (updateMessageDataById.status() != null) {
            this.status = updateMessageDataById.status();
        }
        if (updateMessageDataById.user() != null) {
            this.userId = new User(updateMessageDataById.user());
        }
        if (updateMessageDataById.post() != null) {
            this.postId = new Post(updateMessageDataById.post());
        }
    }
}
