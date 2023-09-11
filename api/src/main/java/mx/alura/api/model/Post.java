package mx.alura.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mx.alura.api.record.post.RegisterPostData;
import mx.alura.api.record.post.UpdatePostByIdData;
import mx.alura.api.record.post.UpdatePostData;
import mx.alura.api.utils.TimestampUtility;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Table(name = "posts")
@Entity(name = "Post")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    @Column(name = "creation_date")
    private Timestamp creationDate;
    @Enumerated(EnumType.STRING)
    private Status status = Status.Opened;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    private Set<Message> messageSet = new HashSet<>();

    public Post(Long id) {
        this.id = id;
    }

    public Post(RegisterPostData registerPostData) {
        this.title = registerPostData.title();
        this.message = registerPostData.message();
        this.creationDate = TimestampUtility.getTimeNowRounded();
        this.status = registerPostData.status();
        this.user = new User(registerPostData.userId());
        this.course = new Course(registerPostData.courseId());
    }
    public void updateData(UpdatePostData updatePostData) {
        if (updatePostData.title() != null) {
            this.title = updatePostData.title();
        }
        if (updatePostData.message() != null) {
            this.message = updatePostData.message();
        }
        if (updatePostData.status() != null) {
            this.status = updatePostData.status();
        }
        if (updatePostData.userId() != null) {
            this.user = new User(updatePostData.userId());
        }
        if (updatePostData.courseId() != null) {
            this.course = new Course(updatePostData.courseId());
        }
    }

    public void updateData(UpdatePostByIdData updatePostByIdData) {
        if (updatePostByIdData.title() != null) {
            this.title = updatePostByIdData.title();
        }
        if (updatePostByIdData.message() != null) {
            this.message = updatePostByIdData.message();
        }
        if (updatePostByIdData.status() != null) {
            this.status = updatePostByIdData.status();
        }
        if (updatePostByIdData.userId() != null) {
            this.user = new User(updatePostByIdData.userId());
        }
        if (updatePostByIdData.courseId() != null) {
            this.course = new Course(updatePostByIdData.courseId());
        }
    }
}
