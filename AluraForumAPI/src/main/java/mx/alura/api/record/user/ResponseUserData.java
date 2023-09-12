package mx.alura.api.record.user;

import mx.alura.api.model.User;

/**
 * A record representing the response data for a user.
 */
public record ResponseUserData(
        Long id,
        String username,
        String email
) {

    /**
     * Constructs a ResponseUserData object based on a User entity.
     *
     * @param user The User entity from which to extract data.
     */
    public ResponseUserData(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
