package mx.alura.api.domain.author;

import jakarta.validation.constraints.NotBlank;

public record AuthorRecordData(
        @NotBlank
        String name,
        @NotBlank
        String surname
) {

}
