package mx.alura.api.domain.author;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "authors")
@Entity(name = "Author")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    public Author(AuthorRecordData authorData) {
        this.name = authorData.name();
        this.surname = authorData.surname();
    }
}
