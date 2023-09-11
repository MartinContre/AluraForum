package mx.alura.api.repository;

import mx.alura.api.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            SELECT p FROM Post p
            WHERE p.user.username=:username
            ORDER BY p.creationDate DESC
            """)
    Page<Post> findByUsername(String username, Pageable pageable);

    Page<Post> findByCourseName(String courseName, Pageable pageable);
}
