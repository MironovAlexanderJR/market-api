package ru.mironov.marketapi.domain.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CustomException> handleValidationFailedException() {
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST.value(), "Validation Failed");
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomException> handleNotFoundException() {
        CustomException customException = new CustomException(HttpStatus.NOT_FOUND.value(), "Item not found");
        return new ResponseEntity<>(customException, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST.value(), "Validation Failed");
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }
}
