package com.example.tam.cnpm.service.retrofit2;

import com.example.tam.cnpm.service.a.response.CategoryResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataClient {
    @GET("category/?format=json")
    Call<ArrayList<CategoryResponse>> getListCategory();
}
