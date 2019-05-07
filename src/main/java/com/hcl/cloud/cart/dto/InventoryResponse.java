package com.hcl.cloud.cart.dto;

/**
 * InventorResponse class.
 * @author kumar_sanjay
 */
public class InventoryResponse {
 /**
  * skuCode - skuCode.
  */
 private String skuCode;
 /**
  * quantity - quantity.
  */
 private int quantity;
 /**
  * activeStatus - activeStatus.
  */
 private boolean activeStatus;
 /**
  * inStock - inStock.
  */
 private boolean inStock;
 /**
  * code - code.
  */
 private int code;
 /**
  * message - message.
  */
 private String message;
 /**
  * Getter method for skuCode.
  * @return skuCode skuCode
  */
 public String getSkuCode() {
  return skuCode;
 }
 /**
  * Setter method for skuCode.
  * @param skuCodeParam skuCodeParam.
  */
 public void setSkuCode(String skuCodeParam) {
  this.skuCode = skuCodeParam;
 }
 /**
  * Getter method for quantity.
  * @return quantity quantity.
  */
 public int getQuantity() {
  return quantity;
 }
 /**
  * Setter method for quantity.
  * @param quantityParam quantityParam
  */
 public void setQuantity(int quantityParam) {
  this.quantity = quantityParam;
 }
 /**
  * Getter method for activeStatus.
  * @return activeStatus activeStatus.
  */
 public boolean isActiveStatus() {
  return activeStatus;
 }
 /**
  * Setter method for activeStatus.
  * @param activeStatusParam activeStatusParam.
  */
 public void setActiveStatus(boolean activeStatusParam) {
  this.activeStatus = activeStatusParam;
 }
 /**
  * Boolean method for isInStock.
  * @return inStock inStock.
  */
 public boolean isInStock() {
  return inStock;
 }
 /**
  * Setter method for isInStock.
  * @param inStockParam inStockParam.
  */
 public void setInStock(boolean inStockParam) {
  this.inStock = inStockParam;
 }
 /**
  * Getter method for code.
  * @return code code.
  */
 public int getCode() {
  return code;
 }
 /**
  * Setter method for code.
  * @param codeParam codeParam.
  */
 public void setCode(int codeParam) {
  this.code = codeParam;
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
 public void setMessage(String messageParam) {
  this.message = messageParam;
 }

}
