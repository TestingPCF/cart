package com.hcl.cloud.cart.dto;

/**
 * @author kumar_sanjay.
 *
 */
public class TokenInfo {

	/**
	 * userId.
	 */
	private String userId;

	/**
	 * status.
	 */
	private int status;

	/**
	 * message.
	 */
	private String message;

	/**
	 * error.
	 */
	private String error;

	/**
	 * Get userId.
	 * @return userId {@link String}.
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(final String error) {
		this.error = error;
	}

}
