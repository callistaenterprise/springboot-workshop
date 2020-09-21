package se.callista.springboot.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     *  Error handling for @Valid exceptions
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }

    /**
     *  General handling for exceptions caused internally
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RestErrorResponse> handleRestServerException(Exception ex) {
        RestErrorResponse errors = new RestErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setErrors(new String[]{ex.getMessage()});
        errors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     *  General handling for exceptions caused by data from client
     */
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<RestErrorResponse> handleRestClientException(Exception ex) {
        RestErrorResponse errors = new RestErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setErrors(new String[]{ex.getMessage()});
        errors.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    /**
     *  Specific handling for exceptions when an entity isn't found
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleDefaultException(Exception ex) {
        RestErrorResponse errors = new RestErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setErrors(new String[]{ex.getMessage()});
        errors.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }

}
