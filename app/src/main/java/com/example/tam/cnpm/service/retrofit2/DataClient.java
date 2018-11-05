package com.example.tam.cnpm.service.retrofit2;

import com.example.tam.cnpm.service.a.response.CategoryResponse;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.MessageResponse;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.service.response.TokenResponse;
import com.example.tam.cnpm.service.response.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface DataClient {
    @GET("category/")
    Call<ArrayList<CategoryResponse>> getListCategory();

    @GET("product/")
    Call<ArrayList<Product>> getListProduct();

    @GET("product/")
    Call<ArrayList<Product>> listProduct(@Query("category") int category,
                                         @Query("format") String format);
    @GET("cart/")
    Call<ArrayList<Cart>> getListCart(@Header("Authorization") String authorization);

    @GET("profile/")
    Call<User> getProfile(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("register/")
    Call<User> getUser(@Field("email")String email,
                       @Field("password") String password,
                       @Field("first_name")String firstName,
                       @Field("last_name")String lastName,
                       @Field("avatar")String avatar,
                       @Field("roll")String role);
    @FormUrlEncoded
    @POST("login/")
    Call<TokenResponse> getToken(@Field("username")String email,
                                 @Field("password") String password);
    @FormUrlEncoded
    @POST("cart/modify/")
    Call<MessageResponse> modifyCart(@Header("Authorization") String authorization,
                                     @Field("product_id") int product_id,
                                     @Field("quantity") int quantity);
    @FormUrlEncoded
    @PUT("profile/")
    Call<User> editProfile(@Header("Authorization") String authorization,
                           @Field("first_name")String firstName,
                           @Field("last_name")String lastName);

}
