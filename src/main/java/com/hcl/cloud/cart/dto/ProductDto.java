package com.hcl.cloud.cart.dto;

/**
 * ProductDto class.
 * @author kumar_sanjay.
 */
public class ProductDto {

 /**
  * skuCode - skuCode.
  */
 private String skuCode;

 /**
  * productName - productName.
  */
 private String productName;

 /**
  * salePrice - salePrice.
  */
 private double salePrice;

 /**
  * listPrice - listPrice.
  */
 private double listPrice;

 /**
  * productDescrition - productDescrition.
  */
 private String productDescrition;

 /**
  * category - category.
  */
 private String category;

 /**
  * is_deleted - is_deleted.
  */
 private boolean is_deleted;

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
  * Getter method for productName.
  * @return productName productName.
  */
 public String getProductName() {
  return productName;
 }

 /**
  * Setter method for productName.
  * @param productNameParam productNameParam.
  */
 public void setProductName(String productNameParam) {
  this.productName = productNameParam;
 }

 /**
  * Getter method for salePrice.
  * @return salePrice salePrice.
  */
 public double getSalePrice() {
  return salePrice;
 }

 /**
  * Setter method for salePrice.
  * @param salePriceParam salePriceParam.
  */
 public void setSalePrice(double salePriceParam) {
  this.salePrice = salePriceParam;
 }

 /**
  * Getter method for listPrice.
  * @return listPrice listPrice.
  */
 public double getListPrice() {
  return listPrice;
 }

 /**
  * Setter method for listPrice.
  * @param listPriceParam listPriceParam.
  */
 public void setListPrice(double listPriceParam) {
  this.listPrice = listPriceParam;
 }

 /**
  * Getter method for productDescrition.
  * @return productDescrition productDescrition
  */
 public String getProductDescrition() {
  return productDescrition;
 }

 /**
  * Setter method for productDescrition.
  * @param productDescritionParam productDescritionParam.
  */
 public void setProductDescrition(String productDescritionParam) {
  this.productDescrition = productDescritionParam;
 }

 /**
  * Getter method for category.
  * @return category category.
  */
 public String getCategory() {
  return category;
 }

 /**
  * Setter method for category.
  * @param categoryParam categoryParam.
  */
 public void setCategory(String categoryParam) {
  this.category = categoryParam;
 }

 /**
  * Getter is_deleted.
  * @return is_deleted is_deleted
  */
 public boolean isIs_deleted() {
  return is_deleted;
 }

 /**
  * Setter is_deleted.
  * @param is_deleted is_deleted
  */
 public void setIs_deleted(boolean is_deleted) {
  this.is_deleted = is_deleted;
 }
}
