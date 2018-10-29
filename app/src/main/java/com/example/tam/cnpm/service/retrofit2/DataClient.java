package com.example.tam.cnpm.service.retrofit2;

import com.example.tam.cnpm.service.a.response.CategoryResponse;
import com.example.tam.cnpm.service.response.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataClient {
    @GET("category/")
    Call<ArrayList<CategoryResponse>> getListCategory();

    @GET("product/")
    Call<ArrayList<Product>> getListProduct();

    @GET("product/")
    Call<ArrayList<Product>> listProduct(@Query("category") int category,
                                         @Query("format") String format);
}
