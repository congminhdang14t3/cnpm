package com.example.tam.cnpm.ui.store;

import android.content.Context;

import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.service.response.Store;
import com.example.tam.cnpm.service.retrofit2.APIUtils;
import com.example.tam.cnpm.ulti.SharedPrefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tam.cnpm.Constant.TOKEN;

public class StorePresenterImpl extends BasePresenter<StoreContract.StoreView> implements StoreContract.StorePresenter {
    public StorePresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void getStoreById(int id) {
        getView().showLoading();
        Call<Store> call = APIUtils.getData().getStore(id);
        call.enqueue(new Callback<Store>() {
            @Override
            public void onResponse(Call<Store> call, Response<Store> response) {
                if (response.isSuccessful()) {
                    getView().init(response.body());
                } else {
                    getView().showError(response.toString());
                }
                getView().dismissLoading();
            }

            @Override
            public void onFailure(Call<Store> call, Throwable t) {
                getView().showError(t.toString());
                getView().dismissLoading();
            }
        });
    }

    @Override
    public void getListProduct(int id) {
        getView().showLoading();
        APIUtils.getData().getListProduct(id)
                .enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        if(response.isSuccessful()){
                            getView().getListProduct(response.body());
                        }else{
                            getView().showToast("Get list product Error");
                        }
                        getView().dismissLoading();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        getView().showToast("Get list product Error");
                        getView().dismissLoading();
                    }
                });
    }
}
