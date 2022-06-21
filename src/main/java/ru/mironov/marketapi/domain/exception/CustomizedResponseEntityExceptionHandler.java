package ru.mironov.marketapi.domain.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<Error> handleValidationFailedException() {
        Error customException = new Error(HttpStatus.BAD_REQUEST.value(), "Validation Failed");
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> handleNotFoundException() {
        Error customException = new Error(HttpStatus.NOT_FOUND.value(), "Item not found");
        return new ResponseEntity<>(customException, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Error error = new Error(status.value(), ex.getMessage());
        return new ResponseEntity<>(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error error = new Error(status.value(), "Validation Failed");
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleAll(RuntimeException ex, WebRequest request) {

        Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
