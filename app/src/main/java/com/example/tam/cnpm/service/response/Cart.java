package com.example.tam.cnpm.service.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart implements Parcelable{

@SerializedName("product")
@Expose
private Product product;
@SerializedName("quantity")
@Expose
private Integer quantity;

    public Cart(){}
    protected Cart(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(product, i);
        if (quantity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(quantity);
        }
    }
}