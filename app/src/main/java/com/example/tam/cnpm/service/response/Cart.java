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


    @Override
    public String toString() {
        return "Cart{" +
                "product=" + product.toString() +
                ", quantity=" + quantity +
                '}';
    }
}