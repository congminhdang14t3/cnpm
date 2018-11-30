package com.example.tam.cnpm.service.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("created")
@Expose
private String created;
@SerializedName("modified")
@Expose
private String modified;
@SerializedName("name")
@Expose
private String name;
@SerializedName("phone")
@Expose
private String phone;
@SerializedName("image")
@Expose
private Object image;
@SerializedName("is_active")
@Expose
private Boolean isActive;
@SerializedName("soft_delete")
@Expose
private Object softDelete;
@SerializedName("address")
@Expose
private String address;
@SerializedName("location")
@Expose
private String location;
@SerializedName("owners")
@Expose
private Integer owners;
@SerializedName("user")
@Expose
private Integer user;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
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

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

public Object getImage() {
return image;
}

public void setImage(Object image) {
this.image = image;
}

public Boolean getIsActive() {
return isActive;
}

public void setIsActive(Boolean isActive) {
this.isActive = isActive;
}

public Object getSoftDelete() {
return softDelete;
}

public void setSoftDelete(Object softDelete) {
this.softDelete = softDelete;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public String getLocation() {
return location;
}

public void setLocation(String location) {
this.location = location;
}

public Integer getOwners() {
return owners;
}

public void setOwners(Integer owners) {
this.owners = owners;
}

public Integer getUser() {
return user;
}

public void setUser(Integer user) {
this.user = user;
}

}