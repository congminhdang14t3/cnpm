package com.example.tam.cnpm.ui.list_product;

import android.content.Context;

import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.service.retrofit2.APIUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPresenterImpl extends BasePresenter<ProductContract.ProductView> implements ProductContract.ProductPresenter {
    public ProductPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void getListProduct(int categoryId) {
        getView().showLoading();
        if(categoryId==0){
            Call<ArrayList<Product>> call = APIUtils.getData().getListProduct();
            call.enqueue(new Callback<ArrayList<Product>>() {
                @Override
                public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                    if(response.isSuccessful()){
                        getView().listProduct(response.body());
                        getView().dismissLoading();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                    getView().showError(t.toString());
                    getView().dismissLoading();
                }
            });
        }
        else{
            Call<ArrayList<Product>> call = APIUtils.getData().listProduct(categoryId,"json");
            call.enqueue(new Callback<ArrayList<Product>>() {
                @Override
                public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                    if(response.isSuccessful()){
                        getView().listProduct(response.body());
                        getView().dismissLoading();
                    }
                    else{
                        getView().showError(response.errorBody().toString());
                        getView().dismissLoading();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                    getView().showError(t.toString());
                    getView().dismissLoading();
                }
            });
        }
    }
}
