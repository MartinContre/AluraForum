package mx.alura.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import mx.alura.api.record.user.RegisterUserData;
import mx.alura.api.record.user.UpdateUserByIdData;
import mx.alura.api.record.user.UpdateUserData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Represents a User entity in the application.
 */
@Table(name = "users")
@Entity(name = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {

    @Schema(description = "User ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Username of the user")
    @Column(name = "name")
    private String username;

    @Schema(description = "Email of the user")
    private String email;

    @Schema(description = "Password of the user")
    private String password;

    /**
     * Constructs a User object with the given ID.
     *
     * @param id The user ID.
     */
    public User(Long id) {
        this.id = id;
    }

    /**
     * Constructs a User object from user registration data.
     *
     * @param userRegisterData The user registration data.
     */
    public User(RegisterUserData userRegisterData) {
        this.username = userRegisterData.name();
        this.email = userRegisterData.email();
        this.password = userRegisterData.password();
    }

    /**
     * Updates the user's data using the provided update data.
     *
     * @param userUpdateData The update data.
     */
    public void updateData(UpdateUserData userUpdateData) {
        if (userUpdateData.name() != null) {
            this.username = userUpdateData.name();
        }

        if (userUpdateData.email() != null) {
            this.email = userUpdateData.email();
        }

        if (userUpdateData.password() != null) {
            this.password = userUpdateData.password();
        }
    }

    /**
     * Updates the user's data using the provided update data.
     *
     * @param updateUserByIdData The update data.
     */
    public void updateData(UpdateUserByIdData updateUserByIdData) {
        if (updateUserByIdData.name() != null) {
            this.username = updateUserByIdData.name();
        }

        if (updateUserByIdData.email() != null) {
            this.email = updateUserByIdData.email();
        }

        if (updateUserByIdData.password() != null) {
            this.password = updateUserByIdData.password();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}
