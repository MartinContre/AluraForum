package mx.alura.api.repository;

import mx.alura.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    public UserDetails findByUsername(String user);

}
