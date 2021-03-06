package com.example.tam.cnpm.service.retrofit2;

import com.example.tam.cnpm.service.a.response.CategoryResponse;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.FeedBack;
import com.example.tam.cnpm.service.response.MessageResponse;
import com.example.tam.cnpm.service.response.Order;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.service.response.Store;
import com.example.tam.cnpm.service.response.TokenResponse;
import com.example.tam.cnpm.service.response.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
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

    @Multipart
    @POST("register/")
    Call<User> registerUser(@Part("email") RequestBody email,
                        @Part("password") RequestBody password,
                        @Part("first_name") RequestBody firstName,
                        @Part("last_name") RequestBody lastName,
                        @Part MultipartBody.Part image,
                        @Part("roll") RequestBody role);

    @Multipart
    @POST("register/")
    Call<User> registerUserNotAvatar(@Part("email") RequestBody email,
                            @Part("password") RequestBody password,
                            @Part("first_name") RequestBody firstName,
                            @Part("last_name") RequestBody lastName,
                            @Part("roll") RequestBody role);

    @FormUrlEncoded
    @POST("login/")
    Call<TokenResponse> getToken(@Field("username") String email,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST("cart/modify/")
    Call<MessageResponse> modifyCart(@Header("Authorization") String authorization,
                                     @Field("product_id") int product_id,
                                     @Field("quantity") int quantity);

    @Multipart
    @PUT("profile/")
    Call<User> editProfile(@Header("Authorization") String authorization,
                           @Part("first_name") RequestBody firstName,
                           @Part("last_name") RequestBody lastName,
                           @Part MultipartBody.Part avatar);
    @Multipart
    @PUT("profile/")
    Call<User> editProfileNotAvatar(@Header("Authorization") String authorization,
                           @Part("first_name") RequestBody firstName,
                           @Part("last_name") RequestBody lastName);

    @GET("feedback/")
    Call<ArrayList<FeedBack>> getListFeedback(@Query("product") int product);

    @FormUrlEncoded
    @POST("feedback/")
    Call<FeedBack> addFeedback(@Header("Authorization") String authorization,
                               @Field("product") int product_id,
                               @Field("store") int store,
                               @Field("detail") String detail,
                               @Field("star") int star);

    @Headers("Content-Type: application/json")
    @POST("paypal/redirect/")
    Call<String> redirect(@Header("Authorization") String authorization,@Body String body);
    @Headers("Content-Type: application/json")
    @POST("paypal/redirect/")
    Call<String> redirectNotToken(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("order/create/")
    Call<MessageResponse> orderShipCode(@Header("Authorization") String authorization,@Body String body);
    @Headers("Content-Type: application/json")
    @POST("order/create/")
    Call<MessageResponse> orderShipCodeNotToken(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("paypal/payment/")
    Call<MessageResponse> orderPayment(@Body String body);

    @GET("order/list/")
    Call<ArrayList<Order>> listOrders(@Header("Authorization") String authorization);

    @GET("store/info/{id}/")
    Call<Store> getStore(@Path("id") int id);

    @FormUrlEncoded
    @PUT("change/password/")
    Call<MessageResponse> changPassword(@Header("Authorization") String authorization,
                                        @Field("new_password") String newPass,
                                        @Field("new_password2") String newPass1,
                                        @Field("old_password") String oldPass);

    @GET("product/")
    Call<ArrayList<Product>> getListProduct(@Query("stores") int stores);
}
