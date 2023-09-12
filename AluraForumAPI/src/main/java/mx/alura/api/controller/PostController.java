package mx.alura.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mx.alura.api.infra.errors.ErrorHandler;
import mx.alura.api.model.Post;
import mx.alura.api.record.post.*;
import mx.alura.api.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/forum/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostMapping
    public ResponseEntity<ResponsePostData> registerPost(
            @RequestBody @Valid RegisterPostData registerPostData,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Post post = postRepository.save(new Post(registerPostData));

        URI uri = uriComponentsBuilder.path("/forum/posts/{id}").buildAndExpand(
                post.getId()
        ).toUri();

        return ResponseEntity.created(uri).body(new ResponsePostData(post));
    }

    @GetMapping
    public ResponseEntity<Page<ListPostData>> listPosts(
            @PageableDefault(size = 5, sort = "title")
            Pageable pageable
    ) {
        return ResponseEntity.ok(postRepository.findAll(pageable).map(ListPostData::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePostData> getPostById(@PathVariable Long id) {
        Post post = postRepository.getReferenceById(id);
        return ResponseEntity.ok(new ResponsePostData(post));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Page<ListPostData>> listPostByUserName(
            @PageableDefault(size = 5) Pageable pageable,
            @PathVariable String username
    ) {
        return ResponseEntity.ok(postRepository.findByUsername(
                username, pageable)
                .map(ListPostData::new)
        );
    }

    @GetMapping("/course/{courseName}")
    public ResponseEntity<Page<ListPostData>> listPostByCourseName(
            @PageableDefault(size = 5) Pageable pageable,
            @PathVariable String courseName
    ) {
        return ResponseEntity.ok(postRepository.findByCourseName(
                courseName, pageable)
                .map(ListPostData::new)
        );
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ResponsePostData> updatePost(@RequestBody @Valid UpdatePostData updatePostData) {
        Post post = postRepository.getReferenceById(updatePostData.id());
        post.updateData(updatePostData);
        return ResponseEntity.ok(new ResponsePostData(post));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponsePostData> updatePotsById(
            @RequestBody @Valid UpdatePostByIdData updateUserByIdData,
            @PathVariable Long id
    ) {
        Post post = postRepository.getReferenceById(id);
        post.updateData(updateUserByIdData);
        return ResponseEntity.ok(new ResponsePostData(post));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        if (postRepository.existsById(id)) {
            Post post = postRepository.getReferenceById(id);
            postRepository.delete(post);
            return ResponseEntity.noContent().build();
        }
        return new ErrorHandler().handlerError404();
    }
}
