package com.example.tam.cnpm.ui.cart;

import android.content.Context;

import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.retrofit2.APIUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenterImpl extends BasePresenter<CartContract.CartView> implements CartContract.CartPresenter {
    public CartPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void getListCart() {
        getView().showLoading();
        Call<ArrayList<Cart>> call = APIUtils.getData().getListCart("Token e7c3ad116f9c2c19a15a8a5a205c824b6e0e8139");

        call.enqueue(new Callback<ArrayList<Cart>>() {
            @Override
            public void onResponse(Call<ArrayList<Cart>> call, Response<ArrayList<Cart>> response) {
                if(response.isSuccessful()){
                    ArrayList<Cart> list = new ArrayList<>();
                    list.addAll(response.body());
                    for(int i=list.size()-1;i>=0;i--){
                        if(i%2==0){
                            Cart cart = list.get(i+1);
                            list.get(i).setTotalPrice(cart.getTotalPrice());
                            list.remove(i+1);
                        }
                    }
                    getView().listCart(list);
                }
                getView().dismissLoading();
            }

            @Override
            public void onFailure(Call<ArrayList<Cart>> call, Throwable t) {
                getView().showError(t.toString());
                getView().dismissLoading();
            }
        });
    }
}
