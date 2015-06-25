package org.javatraining.dao.exception;

/**
 * Created by vika on 23.06.15.
 */
public class EntityIsAlreadyExistException extends RuntimeException {
    public EntityIsAlreadyExistException() {
        super();
    }

    public EntityIsAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public EntityIsAlreadyExistException(String message) {
        super(message);
    }

    public EntityIsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
