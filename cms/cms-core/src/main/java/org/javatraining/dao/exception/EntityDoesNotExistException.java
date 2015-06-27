package org.javatraining.dao.exception;

import javax.ejb.ApplicationException;

/**
 * Created by vika on 23.06.15.
 */
@ApplicationException(rollback = false)
public class EntityDoesNotExistException extends RuntimeException {
    public EntityDoesNotExistException() {
        super();
    }

    public EntityDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public EntityDoesNotExistException(String message) {
        super(message);
    }

    public EntityDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
