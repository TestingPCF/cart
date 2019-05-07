package com.hcl.cloud.cart.dto;

/**
 * TokenInfo class.
 * @author kumar_sanjay.
 */
public class TokenInfo {

	/**
	 * userId - userId.
	 */
	private String userId;

	/**
	 * status - status.
	 */
	private int status;

	/**
	 * message - message.
	 */
	private String message;

	/**
	 * error - error.
	 */
	private String error;

	/**
	 * Getter method for userId.
	 * @return userId {@link String}.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Setter method for userId.
	 * @param userIdParam userIdParam.
	 */
	public void setUserId(final String userIdParam) {
		this.userId = userIdParam;
	}

	/**
	 * Getter method for status.
	 * @return status status.
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Setter method for status.
	 * @param statusParam statusParam.
	 */
	public void setStatus(final int statusParam) {
		this.status = statusParam;
	}

	/**
	 * Getter method for message.
	 * @return message message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter method for message.
	 * @param messageParam messageParam.
	 */
	public void setMessage(final String messageParam) {
		this.message = messageParam;
	}

	/**
	 * Getter method for Error.
	 * @return error.
	 */
	public String getError() {
		return error;
	}

	/**
	 * Setter method for error.
	 * @param errorParam errorParam.
	 */
	public void setError(final String errorParam) {
		this.error = errorParam;
	}

}
