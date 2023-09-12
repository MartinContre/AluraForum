package mx.alura.api.controller;

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

@RestController
@RequestMapping("/forum/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

    @GetMapping
    public ResponseEntity<Page<ResponseUserData>> listUsers(
            @PageableDefault(size = 5, sort = "id")
            Pageable pageable
    ) {
        return ResponseEntity.ok(userRepository.findAll(pageable).map(ResponseUserData::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserData> getUserById(@PathVariable Long id) {
        User user = userRepository.getReferenceById(id);
        return ResponseEntity.ok(new ResponseUserData(user));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ResponseUserData> updateUser(@RequestBody @Valid UpdateUserData updateUserData) {
        User user = userRepository.getReferenceById(updateUserData.id());
        user.updateData(updateUserData);
        return ResponseEntity.ok(new ResponseUserData(user));
    }

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
