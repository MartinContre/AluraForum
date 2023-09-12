package mx.alura.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

/**
 * Controller responsible for managing forum posts.
 */
@RestController
@RequestMapping("/forum/posts")
@SecurityRequirement(name = "bearerAuth")
public class PostController {

    private final PostRepository postRepository;

    /**
     * Constructs a new PostController.
     *
     * @param postRepository The repository for managing posts.
     */
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Registers a new post.
     *
     * @param registerPostData     The data to register a new post.
     * @param uriComponentsBuilder The URI components builder for generating response URI.
     * @return ResponseEntity with the registered post data.
     */
    @Operation(summary = "Register a new post", description = "Registers a new post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post registered successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required.")
    })
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

    /**
     * Lists all posts.
     *
     * @param pageable The pageable request for pagination.
     * @return ResponseEntity with a page of post-data.
     */
    @Operation(summary = "List posts", description = "Lists all posts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts listed successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required.")
    })
    @GetMapping
    public ResponseEntity<Page<ListPostData>> listPosts(
            @PageableDefault(size = 5, sort = "title")
            Pageable pageable
    ) {
        return ResponseEntity.ok(postRepository.findAll(pageable).map(ListPostData::new));
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param id The ID of the post to retrieve.
     * @return ResponseEntity with the post-data if found, or an error response if not found.
     */
    @Operation(summary = "Get post by ID", description = "Retrieves a post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "Post not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponsePostData> getPostById(@PathVariable Long id) {
        Post post = postRepository.getReferenceById(id);
        return ResponseEntity.ok(new ResponsePostData(post));
    }

    /**
     * Retrieves posts by username.
     *
     * @param pageable The pageable request for pagination.
     * @param username The username to filter posts.
     * @return ResponseEntity with a page of post-data filtered by username.
     */
    @Operation(summary = "Get posts by username", description = "Retrieves posts by username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required.")
    })
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

    /**
     * Retrieves posts by course name.
     *
     * @param pageable   The pageable request for pagination.
     * @param courseName The name of the course to filter posts.
     * @return ResponseEntity with a page of post-data filtered by course name.
     */
    @Operation(summary = "Get posts by course name", description = "Retrieves posts by course name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required.")
    })
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

    /**
     * Updates a post's data.
     *
     * @param updatePostData The data for updating the post.
     * @return ResponseEntity with the updated post data if successful, or an error response if not found or the request is invalid.
     */
    @Operation(summary = "Update a post", description = "Updates a post's data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "Post not found.")
    })
    @PutMapping
    @Transactional
    public ResponseEntity<ResponsePostData> updatePost(@RequestBody @Valid UpdatePostData updatePostData) {
        Post post = postRepository.getReferenceById(updatePostData.id());
        post.updateData(updatePostData);
        return ResponseEntity.ok(new ResponsePostData(post));
    }

    /**
     * Updates a post by its ID.
     *
     * @param updateUserByIdData The data for updating the post.
     * @param id                  The ID of the post to update.
     * @return ResponseEntity with the updated post-data if successful, or an error response if not found or the request is invalid.
     */
    @Operation(summary = "Update a post by ID", description = "Updates a post's data by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "Post not found.")
    })
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

    /**
     * Deletes a post by its ID.
     *
     * @param id The ID of the post to delete.
     * @return ResponseEntity with a success message if deleted, or an error response if not found.
     */
    @Operation(summary = "Delete a post", description = "Deletes a post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "Post not found.")
    })
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
