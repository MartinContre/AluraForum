package mx.alura.api.record.user;

import mx.alura.api.model.User;

public record ResponseUserData(
        Long id,
        String username,
        String email
) {

    public ResponseUserData(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
