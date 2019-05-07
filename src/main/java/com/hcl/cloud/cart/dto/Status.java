package com.hcl.cloud.cart.dto;

import org.springframework.http.HttpStatus;

/**
 * Status class for status.
 * @author kumar_sanjay
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
  * @param statusParam statusParam
  * @param messageParam messageParam
  */
 public Status(final HttpStatus statusParam, final String messageParam) {
  super();
  this.status = statusParam;
  this.message = messageParam;
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
  * @param statusParam the status to set.
  */
 public void setStatus(final HttpStatus statusParam) {
  this.status = statusParam;
 }

 /**
  * @return the message
  */
 public String getMessage() {
  return message;
 }

 /**
  * @param messageParam the message to set
  */
 public void setMessage(final String messageParam) {
  this.message = messageParam;
 }

}
