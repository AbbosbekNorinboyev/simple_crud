package uz.pdp.simplecrud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.simplecrud.dto.ErrorDTO;
import uz.pdp.simplecrud.dto.ResponseDTO;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ResponseDTO<Void>> exception(MethodArgumentNotValidException e) {
        List<ErrorDTO> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(errorList -> {
                    String field = errorList.getField();
                    String defaultMessage = errorList.getDefaultMessage();
                    String rejectionValue = String.valueOf(errorList.getRejectedValue());
                    return new ErrorDTO(
                            field,
                            String.format("defaultMessage: %s, rejectionValue: %s", defaultMessage, rejectionValue)
                    );
                }).toList();

        return ResponseEntity.badRequest().body(
                ResponseDTO.<Void>builder()
                        .code(-1)
                        .message("Validation error")
                        .errors(errors)
                        .build()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseDTO<Void> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(resourceNotFoundException.getMessage())
                .success(false)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseDTO<Void> handleException(Exception exception) {
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Something wrong -> " + exception.getMessage())
                .success(false)
                .build();
    }
}
