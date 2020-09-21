package se.callista.springboot.rest.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String entity, String id) {
        super(entity + " with id " + id + " not found");
    }

}
