package se.callista.springboot.rest.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RestErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String[] errors;
}
