package mx.alura.api.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Global error handler for handling exceptions and providing appropriate responses.
 */
@RestControllerAdvice
public class ErrorHandler {

    /**
     * Handles EntityNotFoundException and returns a 404 Not Found response.
     *
     * @return ResponseEntity with a 404 status code and a message.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handlerError404() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record Not found");
    }

    /**
     * Handles MethodArgumentNotValidException and returns a 400 Bad Request response with validation errors.
     *
     * @param e The MethodArgumentNotValidException containing validation errors.
     * @return ResponseEntity with a 400 status code and a list of validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<DataErrorValidation>> handlerError400(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors().stream().map(DataErrorValidation::new).toList();

        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles SQLIntegrityConstraintViolationException and returns a 400 Bad Request response with an integrity constraints error message.
     *
     * @param e The SQLIntegrityConstraintViolationException.
     * @return ResponseEntity with a 400 status code and an error message.
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIntegrityConstraintViolation(SQLIntegrityConstraintViolationException e) {
        Logger.getLogger("Error", e.getMessage());
        String errorMessage = "The operation could not be completed due to integrity constraints.";
        return ResponseEntity.badRequest().body(errorMessage);
    }

    /**
     * Handles ConstraintViolationException and returns a 400 Bad Request response with an integrity constraints error message.
     *
     * @param e The ConstraintViolationException.
     * @return ResponseEntity with a 400 status code and an error message.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
        String errorMessage = "The operation could not be completed due to integrity constraints.";
        Logger.getLogger("Error", e.getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }

    /**
     * Handles HttpMessageNotReadableException and returns a 400 Bad Request response with the error message.
     *
     * @param e The HttpMessageNotReadableException.
     * @return ResponseEntity with a 400 status code and the error message.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles SQLException and returns a 400 Bad Request response with the error message.
     *
     * @param e The SQLException.
     * @return ResponseEntity with a 400 status code and the error message.
     */
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleSQLException(SQLException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles MissingResourceException and returns a 400 Bad Request response with the error message.
     * If the error message contains "Duplicate entry," a more specific error message is returned.
     *
     * @param e The MissingResourceException.
     * @return ResponseEntity with a 400 status code and the error message.
     */
    @ExceptionHandler(java.util.MissingResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMissingResourceException(java.util.MissingResourceException e) {
        String errorMessage = e.getMessage();
        Logger.getLogger("Error").severe(errorMessage);

        if (errorMessage.contains("Duplicate entry")) {
            String specificErrorMessage = errorMessage.substring(errorMessage.indexOf("Duplicate entry"));
            return ResponseEntity.badRequest().body(specificErrorMessage);
        }

        return ResponseEntity.badRequest().body(errorMessage);
    }


    /**
     * Represents a validation error with field and error details.
     */
    public record DataErrorValidation(String field, String error) {
        public DataErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
