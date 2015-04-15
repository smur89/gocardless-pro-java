package com.gocardless.pro.exceptions;

import java.util.List;

import com.gocardless.pro.exceptions.ApiErrorResponse.ApiError;
import com.gocardless.pro.exceptions.ApiErrorResponse.ErrorType;

/**
 * Base class for exceptions that are thrown as a result of error responses from
 * the API.
 */
public class GoCardlessApiException extends GoCardlessException {
    private final ApiErrorResponse error;

    public GoCardlessApiException(ApiErrorResponse error) {
        super(error.getMessage());
        this.error = error;
    }

    /**
     * Returns the type of the error.
     */
    public ErrorType getType() {
        return error.getType();
    }

    /**
     * Returns the URL to the documentation describing the error.
     */
    public String getDocumentationUrl() {
        return error.getDocumentationUrl();
    }

    /**
     * Returns the ID of the request.  This can be used to help the support
     * team find your error quickly.
     */
    public String getRequestId() {
        return error.getRequestId();
    }

    /**
     * Returns the HTTP status code.
     */
    public int getCode() {
        return error.getCode();
    }

    /**
     * Returns a list of errors.
     */
    public List<ApiError> getErrors() {
        return error.getErrors();
    }
}
