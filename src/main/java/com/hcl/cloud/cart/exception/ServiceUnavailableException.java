package com.hcl.cloud.cart.exception;

import org.springframework.http.HttpStatus;

/**
 * @author kumar_sanjay checked Exception which needs to be checked when request is invalid.
 */
public class ServiceUnavailableException extends RuntimeException{
    
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * message - Exception Specific message.
	 */
    private final String message;
    
    /**
	 * HttpStatus.
	 */
    private final HttpStatus httpStatus;

    /**
	 * Constructs a {@link ServiceUnavailableException} with <code>message</code> as its detailed message.
	 * @param message String
	 * @param httpStatus HttpStatus
	 */
    public ServiceUnavailableException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Getter method for message.
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Getter method for httpStatus.
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
