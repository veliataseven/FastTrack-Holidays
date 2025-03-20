package com.airfranceklm.fasttrack.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) for structured error responses.
 * This class is used to return error details when an exception occurs, including
 * the error message, any additional details, and the HTTP status code.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {

    /**
     * The main error message describing the problem.
     * This message provides a high-level explanation of the error (e.g., "Validation failed").
     */
    private String message;

    /**
     * A list of additional details or specific validation errors that provide more context about the error.
     * For example, field-specific validation errors.
     */
    private List<String> details;

    /**
     * The HTTP status code associated with the error response.
     * For example, 400 for bad request, 500 for internal server error.
     */
    private int statusCode;
}
