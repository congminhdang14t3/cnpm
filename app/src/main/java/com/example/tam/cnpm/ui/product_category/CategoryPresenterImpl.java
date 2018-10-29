package com.example.tam.cnpm.ui.product_category;

import android.content.Context;

import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.a.response.CategoryResponse;
import com.example.tam.cnpm.service.retrofit2.APIUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPresenterImpl extends BasePresenter<CategoryContract.CategoryView> implements CategoryContract.CategoryPresenter{
    public CategoryPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void getListCategory() {
        getView().showLoading();
        Call<ArrayList<CategoryResponse>> call = APIUtils.getData().getListCategory();
        call.enqueue(new Callback<ArrayList<CategoryResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryResponse>> call, Response<ArrayList<CategoryResponse>> response) {
                if(response.isSuccessful()){
                    getView().getListCategory(response.body());
                    getView().dismissLoading();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryResponse>> call, Throwable t) {
                getView().showError(t.toString());
                getView().dismissLoading();
            }
        });
    }


}
