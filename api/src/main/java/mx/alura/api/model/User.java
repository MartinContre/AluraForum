package mx.alura.api.model;

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

@Table(name = "users")
@Entity(name = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String username;
    private String email;
    private String password;

    public User(Long id) {
        this.id = id;
    }

    public User(RegisterUserData userRegisterData) {
        this.username = userRegisterData.name();
        this.email = userRegisterData.email();
        this.password = userRegisterData.password();
    }

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
