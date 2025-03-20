package com.airfranceklm.fasttrack.assignment.exception;

/**
 * Custom exception for handling invalid holiday operations.
 * This exception is thrown when a holiday violates business rules such as overlap,
 * minimum lead time, or if an attempt to delete a non-existing holiday is made.
 */
public class InvalidHolidayException extends RuntimeException {

    /**
     * Constructs a new `InvalidHolidayException` with the specified message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public InvalidHolidayException(String message) {
        super(message);
    }

    /**
     * Constructs a new `InvalidHolidayException` with the specified message and cause.
     *
     * @param message The detail message explaining the reason for the exception.
     * @param cause The cause of the exception.
     */
    public InvalidHolidayException(String message, Throwable cause) {
        super(message, cause);
    }
}

