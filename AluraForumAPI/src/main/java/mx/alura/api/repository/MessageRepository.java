package mx.alura.api.repository;

import mx.alura.api.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("""
                SELECT m FROM Message m WHERE m.postId.course.name=:courseName
            """)
    Page<Message> findByCourseName(String courseName, Pageable pageable);

    @Query("""
                SELECT m FROM Message m WHERE m.userId.username=:username
            """)
    Page<Message> findByUsername(String username, Pageable pageable);
}
