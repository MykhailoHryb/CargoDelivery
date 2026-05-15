package com.final_project.cargo_delivery.exception;

/**
 * ApplicationException
 *
 * @author Mykhailo Hryb
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
