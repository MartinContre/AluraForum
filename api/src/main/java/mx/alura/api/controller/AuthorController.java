package mx.alura.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mx.alura.api.domain.author.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping
    public ResponseEntity<AuthorResponseData> registerAuthor(@RequestBody @Valid AuthorRecordData authorRecordData,
                                                             UriComponentsBuilder uriComponentsBuilder
    ) {
        Author author = authorRepository.save(new Author(authorRecordData));

        URI uri = uriComponentsBuilder.path("authors/{id}").buildAndExpand(author.getId()).toUri();

        return ResponseEntity.created(uri).body(new AuthorResponseData(author));
    }

    @GetMapping
    public ResponseEntity<Page<AuthorResponseData>> getListAuthors(
            @PageableDefault(size = 5, sort = "name")
            Pageable pageable
    ) {
        return ResponseEntity.ok(authorRepository.findAll(pageable).map(AuthorResponseData::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseData> getAuthorById(@PathVariable Long id){
        Author author = authorRepository.getReferenceById(id);
        return ResponseEntity.ok(new AuthorResponseData(author));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AuthorResponseData> updateAuthorById(@PathVariable Long id,
                                                         @RequestBody @Valid AuthorUpdateData authorUpdateData
                                                         ) {
        Author author = authorRepository.getReferenceById(id);
        author.updateData(authorUpdateData);
        return ResponseEntity.ok(new AuthorResponseData(author));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<AuthorResponseData> deleteAuthorById(@PathVariable Long id) {
        Author author = authorRepository.getReferenceById(id);
        authorRepository.delete(author);
        return ResponseEntity.noContent().build();
    }

}
