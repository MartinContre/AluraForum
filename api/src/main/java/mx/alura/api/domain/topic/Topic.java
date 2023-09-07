package mx.alura.api.domain.topic;

import jakarta.persistence.*;
import lombok.*;
import mx.alura.api.utils.TimestampUtility;

import java.sql.Timestamp;

@Table(name = "posts")
@Entity(name = "Topic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    @Column(name = "creation_date")
    private Timestamp creationDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "author_id")
    private String authorId;
    @Enumerated(EnumType.STRING)
    private Course course;

    public Topic(TopicRecordData topicRecordData) {
        this.title = topicRecordData.title();
        this.message = topicRecordData.message();
        this.creationDate = setTimestamp();
        this.status = topicRecordData.status();
        this.authorId = topicRecordData.authorId();
        this.course = topicRecordData.course();
    }

    private Timestamp setTimestamp() {
        return TimestampUtility.getTimeNowRounded();
    }

    public void updateData(TopicUpdateData topicUpdateData) {
        if (topicUpdateData.title() != null) {
            this.title = topicUpdateData.title();
        }

        if (topicUpdateData.message() != null) {
            this.message = topicUpdateData.message();
        }

        if (topicUpdateData.status() != null) {
            this.status = topicUpdateData.status();
        }

        if (topicUpdateData.authorId() != null) {
            this.authorId = topicUpdateData.authorId();
        }

        if (topicUpdateData.course() != null) {
            this.course = topicUpdateData.course();
        }
    }
}
