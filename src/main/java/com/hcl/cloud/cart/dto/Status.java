package com.hcl.cloud.cart.dto;

import org.springframework.http.HttpStatus;

/**
 * Status class for status.
 */
public class Status {

	/**
	 * Http Status.
	 */
	private HttpStatus status;

	/**
	 * Status Message.
	 */
	private String message;

	/**
	 * Default Constructor.
	 */
	public Status() {
		// TODO Auto-generated method stub
	}

	/**
	 * fully parameterized constructor.
	 * @param status
	 * @param message
	 */
	public Status(final HttpStatus status, final String message) {
		super();
		this.status = status;
		this.message = message;
	}

	/**
	 * @return Status code..
	 */
	public int getCode() {
		return status().value();
	}

	/**
	 * @return the status
	 */
	public HttpStatus status() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(final HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

}
