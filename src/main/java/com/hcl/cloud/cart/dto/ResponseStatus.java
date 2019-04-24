package com.hcl.cloud.cart.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author kumar_sanjay
 * @param <E>
 */
@JsonInclude(Include.NON_NULL)
public final class ResponseStatus<E> {

	/**
	 * status containing message and HttpStatus code.
	 */
	private final Status status;

	/**
	 * Entity containing data.
	 */
	private final E data;

	/**
	 * Collection having resource data.
	 */
	private final Collection<E> dataSet;

	/**
	 * @author kumar_sanjay Builder to set corresponding values.
	 * @param <E>
	 */
	public static class Builder<E> {
		/**
		 * data.
		 */
		private E data;

		/**
		 * status.
		 */
		private Status status;

		/**
		 * dataSet.
		 */
		private Collection<E> dataSet;

		/**
		 * Object instantiation with mandatory fields.
		 * @param status
		 */
		public Builder(final Status status) {
			this.status = status;
		}

		/**
		 * Builder<E>.
		 * @param data
		 * @return builder.
		 */
		public final Builder<E> setEntity(final E data) {
			this.data = data;
			return this;
		}

		/**
		 * Builder<E>.
		 * @param dataSet
		 * @return builder.
		 */
		public final Builder<E> setCollection(final Collection<E> dataSet) {
			this.dataSet = dataSet;
			return this;
		}

		/**
		 * ResponseStatus<E>.
		 * @return responseStatus.
		 */
		public final ResponseStatus<E> build() {
			return new ResponseStatus<E>(this);
		}
	}

	/**
	 * setting builder values.
	 * @param builder
	 */
	private ResponseStatus(final Builder<E> builder) {
		this.data = builder.data;
		this.status = builder.status;
		this.dataSet = builder.dataSet;
	}

	/**
	 * @return status.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Gets the data.
	 * @return data.
	 */
	public E getData() {
		return data;
	}

	/**
	 * @return Collection
	 */
	public Collection<E> getDataSet() {
		return dataSet;
	}

}
