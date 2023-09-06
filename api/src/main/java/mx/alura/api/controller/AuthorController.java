package mx.alura.api.controller;

import jakarta.validation.Valid;
import mx.alura.api.domain.author.Author;
import mx.alura.api.domain.author.AuthorRecordData;
import mx.alura.api.domain.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping
    public void registerAuthor(@RequestBody @Valid AuthorRecordData authorRecordData) {
        authorRepository.save(new Author(authorRecordData));
    }

}
