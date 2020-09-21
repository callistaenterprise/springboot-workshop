package se.callista.springboot.rest.exception;

public class RestClientException extends RuntimeException{

    public RestClientException(String message) {
        super(message);
    }
}
