package mx.alura.api.model;

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

@Table(name = "messages")
@Entity(name = "Message")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @Column(name = "creation_date")
    private Timestamp creationDate;
    private Boolean status = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post postId;

    public Message(RegisterMessageData registerMessageData) {
        this.message = registerMessageData.message();
        this.creationDate = TimestampUtility.getTimeNowRounded();
        this.userId = new User(registerMessageData.user());
        this.postId = new Post(registerMessageData.post());
    }

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
