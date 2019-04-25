package com.hcl.cloud.cart.exception;

import org.springframework.http.HttpStatus;

/**
 * @author kumar_sanjay checked Exception which needs to be checked when request is invalid.
 */
public class CustomException extends RuntimeException{
	
	/**
	 * @author kumar_sanjay checked Exception which needs to be checked when request is invalid.
	 */
    private static final long serialVersionUID = 1L;

    /**
	 * Exception Specific message.
	 */
    private final String message;
    
    /**
	 * HttpStatus.
	 */
    private final HttpStatus httpStatus;

    /**
	 * Constructs a {@link CustomException} with <code>message</code> as its detailed message.
	 * @param message String
	 */
    public CustomException(String message, HttpStatus httpStatus) {
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
     * Setter method for httpStatus.
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
