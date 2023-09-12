package mx.alura.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mx.alura.api.infra.errors.ErrorHandler;
import mx.alura.api.model.User;
import mx.alura.api.record.user.RegisterUserData;
import mx.alura.api.record.user.ResponseUserData;
import mx.alura.api.record.user.UpdateUserByIdData;
import mx.alura.api.record.user.UpdateUserData;
import mx.alura.api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Controller responsible for managing forum users.
 */
@RestController
@RequestMapping("/forum/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new UserController.
     *
     * @param userRepository   The repository for managing users.
     * @param passwordEncoder The password encoder for encrypting user passwords.
     */
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user.
     *
     * @param registerUserData     The data to register a new user.
     * @param uriComponentsBuilder The URI components builder for generating response URI.
     * @return ResponseEntity with the registered user data.
     */
    @Operation(summary = "Register a new user", description = "Registers a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required.")
    })
    @PostMapping
    public ResponseEntity<ResponseUserData> registerUser(
            @RequestBody @Valid RegisterUserData registerUserData,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        User user = new User(registerUserData);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        URI uri = uriComponentsBuilder.path("/forum/users/{id}").buildAndExpand(
                user.getId()
        ).toUri();

        return ResponseEntity.created(uri).body(new ResponseUserData(user));
    }

    /**
     * Lists all users.
     *
     * @param pageable The pageable request for pagination.
     * @return ResponseEntity with a page of user data.
     */
    @Operation(summary = "List users", description = "Lists all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users listed successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required.")
    })
    @GetMapping
    public ResponseEntity<Page<ResponseUserData>> listUsers(
            @PageableDefault(size = 5, sort = "id")
            Pageable pageable
    ) {
        return ResponseEntity.ok(userRepository.findAll(pageable).map(ResponseUserData::new));
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity with the user data if found, or an error response if not found.
     */
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserData> getUserById(@PathVariable Long id) {
        User user = userRepository.getReferenceById(id);
        return ResponseEntity.ok(new ResponseUserData(user));
    }

    /**
     * Updates a user's data.
     *
     * @param updateUserData The data for updating the user.
     * @return ResponseEntity with the updated user data if successful, or an error response if not found or the request is invalid.
     */
    @Operation(summary = "Update user", description = "Updates a user's data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @PutMapping
    @Transactional
    public ResponseEntity<ResponseUserData> updateUser(@RequestBody @Valid UpdateUserData updateUserData) {
        User user = userRepository.getReferenceById(updateUserData.id());
        user.updateData(updateUserData);
        return ResponseEntity.ok(new ResponseUserData(user));
    }

    /**
     * Updates a user by their ID.
     *
     * @param updateUserByIdData The data for updating the user.
     * @param id                 The ID of the user to update.
     * @return ResponseEntity with the updated user data if successful, or an error response if not found or the request is invalid.
     */
    @Operation(summary = "Update user by ID", description = "Updates a user's data by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseUserData> updateUserById(
            @RequestBody @Valid UpdateUserByIdData updateUserByIdData,
            @PathVariable Long id
    ) {
        User user = userRepository.getReferenceById(id);
        user.updateData(updateUserByIdData);
        return ResponseEntity.ok(new ResponseUserData(user));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return ResponseEntity with a success message if deleted, or an error response if not found.
     */
    @Operation(summary = "Delete user", description = "Deletes a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.getReferenceById(id);
            userRepository.delete(user);
            return ResponseEntity.noContent().build();
        }
        return new ErrorHandler().handlerError404();
    }
}
