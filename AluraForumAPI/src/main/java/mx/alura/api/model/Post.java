package mx.alura.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

/**
 * Represents a post in the system.
 */
@Table(name = "posts")
@Entity(name = "Post")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post {

    @Schema(description = "Post ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Title of the post.")
    private String title;

    @Schema(description = "Content of the post.")
    private String message;

    @Schema(description = "The creation date of the post.")
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Schema(description = "The status for the post, Opened default.")
    @Enumerated(EnumType.STRING)
    private Status status = Status.Opened;

    @Schema(description = "The user id who posted the post.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Schema(description = "The course id to which the message belongs.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Schema(description = "The set of messages associated with this post.")
    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    private Set<Message> messageSet = new HashSet<>();

    /**
     * Constructs a new Post object with the specified ID.
     *
     * @param id The post-ID.
     */
    public Post(Long id) {
        this.id = id;
    }

    /**
     * Creates a new post with the provided data.
     *
     * @param registerPostData The data to register the post.
     */
    public Post(RegisterPostData registerPostData) {
        this.title = registerPostData.title();
        this.message = registerPostData.message();
        this.creationDate = TimestampUtility.getTimeNowRounded();
        this.status = registerPostData.status();
        this.user = new User(registerPostData.userId());
        this.course = new Course(registerPostData.courseId());
    }

    /**
     * Updates the post-data with the provided data.
     *
     * @param updatePostData The data to update the post.
     */
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

    /**
     * Updates the post-data with the provided data.
     *
     * @param updatePostByIdData The data to update the post.
     */
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
