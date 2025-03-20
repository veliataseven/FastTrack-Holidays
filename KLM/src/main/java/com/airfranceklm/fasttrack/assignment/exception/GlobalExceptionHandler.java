package com.airfranceklm.fasttrack.assignment.exception;

import com.airfranceklm.fasttrack.assignment.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler for handling various exceptions across the application.
 * This class provides centralized exception handling for validation errors,
 * custom exceptions, and other unexpected errors.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors (MethodArgumentNotValidException).
     *
     * @param ex The exception containing details of the validation errors.
     * @return The error response containing the details of the validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Validation failed",
                errorDetails,
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles InvalidHolidayException, which is thrown when a holiday operation fails due to invalid data.
     *
     * @param ex The exception containing the error message for invalid holiday operations.
     * @return The error response containing the exception message and HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(InvalidHolidayException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidHolidayException(InvalidHolidayException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ex.getMessage(),
                List.of(),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other unexpected exceptions (generic exceptions).
     *
     * @param ex The exception that was thrown.
     * @return A generic error response containing a message and HTTP status INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "An unexpected error occurred",
                List.of(ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
