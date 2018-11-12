package com.example.tam.cnpm.service.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedBack {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("customer")
@Expose
private String customer;
@SerializedName("created")
@Expose
private String created;
@SerializedName("modified")
@Expose
private String modified;
@SerializedName("detail")
@Expose
private String detail;
@SerializedName("star")
@Expose
private Integer star;
@SerializedName("store")
@Expose
private Integer store;
@SerializedName("product")
@Expose
private Integer product;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getCustomer() {
return customer;
}

public void setCustomer(String customer) {
this.customer = customer;
}

public String getCreated() {
return created;
}

public void setCreated(String created) {
this.created = created;
}

public String getModified() {
return modified;
}

public void setModified(String modified) {
this.modified = modified;
}

public String getDetail() {
return detail;
}

public void setDetail(String detail) {
this.detail = detail;
}

public Integer getStar() {
return star;
}

public void setStar(Integer star) {
this.star = star;
}

public Integer getStore() {
return store;
}

public void setStore(Integer store) {
this.store = store;
}

public Integer getProduct() {
return product;
}

public void setProduct(Integer product) {
this.product = product;
}

}