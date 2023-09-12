package mx.alura.api.controller;

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

@RestController
@RequestMapping("/forum/messages")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

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

    @GetMapping
    public ResponseEntity<Page<ListMessageData>> listMessages(
            @PageableDefault(size = 5, sort = "creationDate")
            Pageable pageable
    ) {
        return ResponseEntity.ok(messageRepository.findAll(pageable).map(ListMessageData::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessageData> getMessageById(@PathVariable Long id) {
        Message message = messageRepository.getReferenceById(id);
        return ResponseEntity.ok(new ResponseMessageData(message));
    }

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

    @PutMapping
    @Transactional
    public ResponseEntity<ResponseMessageData> updateMessage(@RequestBody @Valid UpdateMessageData updateMessageData) {
        Message message = messageRepository.getReferenceById(updateMessageData.id());
        message.updateData(updateMessageData);
        return ResponseEntity.ok(new ResponseMessageData(message));
    }

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
