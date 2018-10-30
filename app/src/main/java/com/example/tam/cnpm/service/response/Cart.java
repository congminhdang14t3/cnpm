package com.example.tam.cnpm.service.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {

@SerializedName("product")
@Expose
private Product product;
@SerializedName("quantity")
@Expose
private Integer quantity;
@SerializedName("total_price")
@Expose
private Integer totalPrice;

public Product getProduct() {
return product;
}

public void setProduct(Product product) {
this.product = product;
}

public Integer getQuantity() {
return quantity;
}

public void setQuantity(Integer quantity) {
this.quantity = quantity;
}

public Integer getTotalPrice() {
return totalPrice;
}

public void setTotalPrice(Integer totalPrice) {
this.totalPrice = totalPrice;
}

}