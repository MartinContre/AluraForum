package mx.alura.api.domain.users;

public record UserAuthenticationData(
        String name,
        String password
) {
}
