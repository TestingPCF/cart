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
   * data - data.
   */
  private E data;

  /**
   * status - status.
   */
  private Status status;

  /**
   * dataSet - dataSet.
   */
  private Collection<E> dataSet;

  /**
   * Object instantiation with mandatory fields.
   * @param statusParam statusParam
   */
  public Builder(final Status statusParam) {
   this.status = statusParam;
  }

  /**
   * Builder<E>.
   * @param dataParam dataParam.
   * @return builder builder.
   */
  public final Builder<E> setEntity(final E dataParam) {
   this.data = dataParam;
   return this;
  }

  /**
   * Builder<E>.
   * @param dataSetParam dataSetParam.
   * @return builder builder.
   */
  public final Builder<E> setCollection(final Collection<E> dataSetParam) {
   this.dataSet = dataSetParam;
   return this;
  }

  /**
   * ResponseStatus<E> response status.
   * @return responseStatus.
   */
  public final ResponseStatus<E> build() {
   return new ResponseStatus<E>(this);
  }
 }

 /**
  * setting builder values.
  * @param builder builder
  */
 private ResponseStatus(final Builder<E> builder) {
  this.data = builder.data;
  this.status = builder.status;
  this.dataSet = builder.dataSet;
 }

 /**
  * Getter method for status.
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
  * Getter method for dataSet.
  * @return Collection Collection
  */
 public Collection<E> getDataSet() {
  return dataSet;
 }

}
