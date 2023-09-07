package mx.alura.api.domain.author;

public record AuthorResponseData(
        Long id,
        String name,
        String surname
) {

    public AuthorResponseData(Author author) {
        this(
                author.getId(),
                author.getName(),
                author.getSurname()
        );
    }

}
