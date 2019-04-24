package com.hcl.cloud.cart.exception;

/**
 * @author kumar_sanjay checked Exception which needs to be checked when request is invalid.
 */
public class BadRequestException extends Exception {

	/**
	 * serial version Uid can be used in process of serialization.
	 */
	private static final long serialVersionUID = 9015971438054518672L;

	/**
	 * Exception Specific message.
	 */
	private String message;

	/**
	 * Constructs a {@link BadRequestException} with <code>null</code> as its detailed message.
	 */
	public BadRequestException() {
		super();
	}

	/**
	 * Constructs a {@link BadRequestException} with <code>message</code> as its detailed message.
	 * @param message String
	 */
	public BadRequestException(final String message) {
		super(message);
		this.message = message;
	}

	/**
	 * Constructs a {@link BadRequestException} with the specified. detailed message and its cause;
	 * @param message String
	 * @param cause Throwable
	 */
	public BadRequestException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * returns {@code message} .
	 * @return message.
	 */
	public final String getMessage() {
		return message;
	}

	/**
	 * sets a particular message seperately.
	 * @param message String
	 */
	public final void setMessage(final String message) {
		this.message = message;
	}

}
