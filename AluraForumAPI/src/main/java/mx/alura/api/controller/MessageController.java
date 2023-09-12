package mx.alura.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mx.alura.api.infra.errors.ErrorHandler;
import mx.alura.api.model.Message;
import mx.alura.api.record.message.*;
import mx.alura.api.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Controller responsible for managing forum messages.
 */
@RestController
@RequestMapping("/forum/messages")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {

    private final MessageRepository messageRepository;

    /**
     * Constructs a new MessageController.
     *
     * @param messageRepository The repository for managing messages.
     */
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Registers a new message.
     *
     * @param registerMessageData   The data to register a new message.
     * @param uriComponentsBuilder The URI components builder for generating response URI.
     * @return ResponseEntity with the registered message data.
     */
    @Operation(summary = "Register a new message", description = "Registers a new message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message registered successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required.")
    })
    @PostMapping
    public ResponseEntity<ResponseMessageData> registerMessage(
            @RequestBody @Valid RegisterMessageData registerMessageData,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Message message = messageRepository.save(new Message(registerMessageData));

        URI uri = uriComponentsBuilder.path("/forum/posts/{id}").buildAndExpand(
                message.getId()
        ).toUri();

        return ResponseEntity.created(uri).body(new ResponseMessageData(message));
    }

    /**
     * Lists all messages.
     *
     * @param pageable The pageable request for pagination.
     * @return ResponseEntity with a page of message data.
     */
    @Operation(summary = "List messages", description = "Lists all messages.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages listed successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required.")
    })
    @GetMapping
    public ResponseEntity<Page<ListMessageData>> listMessages(
            @PageableDefault(size = 5, sort = "creationDate")
            Pageable pageable
    ) {
        return ResponseEntity.ok(messageRepository.findAll(pageable).map(ListMessageData::new));
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param id The ID of the message to retrieve.
     * @return ResponseEntity with the message data if found, or an error response if not found.
     */
    @Operation(summary = "Get message by ID", description = "Retrieves a message by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "Message not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessageData> getMessageById(@PathVariable Long id) {
        Message message = messageRepository.getReferenceById(id);
        return ResponseEntity.ok(new ResponseMessageData(message));
    }

    /**
     * Retrieves messages by course name.
     *
     * @param pageable   The pageable request for pagination.
     * @param courseName The name of the course to filter messages.
     * @return ResponseEntity with a page of message data filtered by course name.
     */
    @Operation(summary = "Get messages by course name", description = "Retrieves messages by course name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required.")
    })
    @GetMapping("/course/{courseName}")
    public ResponseEntity<Page<ListMessageData>> getMessageByCourseName(
            @PageableDefault(size = 5) Pageable pageable,
            @PathVariable String courseName
    ) {
        return ResponseEntity.ok(
                messageRepository.findByCourseName(courseName, pageable)
                        .map(ListMessageData::new)
                );
    }

    /**
     * Retrieves messages by username.
     *
     * @param pageable The pageable request for pagination.
     * @param username The username to filter messages.
     * @return ResponseEntity with a page of message data filtered by username.
     */
    @Operation(summary = "Get messages by username", description = "Retrieves messages by username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages retrieved successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required.")
    })
    @GetMapping("/user/{username}")
    public ResponseEntity<Page<ListMessageData>> getMessageByUsername(
            @PageableDefault(size = 5) Pageable pageable,
            @PathVariable String username
    ) {
        return ResponseEntity.ok(
                messageRepository.findByUsername(username, pageable)
                        .map(ListMessageData::new)
                );
    }

    /**
     * Updates a message's data.
     *
     * @param updateMessageData The data for updating the message.
     * @return ResponseEntity with the updated message data if successful,
     * or an error response if not found or the request is invalid.
     */
    @Operation(summary = "Update a message", description = "Updates a message's data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "Message not found.")
    })
    @PutMapping
    @Transactional
    public ResponseEntity<ResponseMessageData> updateMessage(@RequestBody @Valid UpdateMessageData updateMessageData) {
        Message message = messageRepository.getReferenceById(updateMessageData.id());
        message.updateData(updateMessageData);
        return ResponseEntity.ok(new ResponseMessageData(message));
    }

    /**
     * Updates a message by its ID.
     *
     * @param updateMessageDataById The data for updating the message.
     * @param id                    The ID of the message to update.
     * @return ResponseEntity with the updated message data if successful,
     * or an error response if not found or the request is invalid.
     */
    @Operation(summary = "Update a message by ID", description = "Updates a message's data by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required."),
            @ApiResponse(responseCode = "404", description = "Message not found.")
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseMessageData> updatePotsById(
            @RequestBody @Valid UpdateMessageDataById updateMessageDataById,
            @PathVariable Long id
    ) {
        Message message = messageRepository.getReferenceById(id);
        message.updateData(updateMessageDataById);
        return ResponseEntity.ok(new ResponseMessageData(message));
    }

    /**
     * Deletes a message by its ID.
     *
     * @param id The ID of the message to delete.
     * @return ResponseEntity with a success message if deleted, or an error response if not found.
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        if (messageRepository.existsById(id)) {
            Message message = messageRepository.getReferenceById(id);
            messageRepository.delete(message);
            return ResponseEntity.noContent().build();
        }
        return new ErrorHandler().handlerError404();
    }
}
