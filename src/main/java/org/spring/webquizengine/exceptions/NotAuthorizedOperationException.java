package org.spring.webquizengine.exceptions;

public class NotAuthorizedOperationException extends RuntimeException {

    public NotAuthorizedOperationException() {
    }

    public NotAuthorizedOperationException(String message) {
        super(message);
    }
}
