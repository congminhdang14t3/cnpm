package com.example.tam.cnpm.ui.cart;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.retrofit2.APIUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tam.cnpm.Constant.SHARED_PREFERENCES_NAME;

public class CartPresenterImpl extends BasePresenter<CartContract.CartView> implements CartContract.CartPresenter {
    public CartPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void getListCart() {
        //6daa91518b9f93ca4a4e97d03074c0d917b26b71
        SharedPreferences sharedPreferences =  getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Constant.TOKEN,"");
        if(!token.equals("")){
            getView().showLoading();

            Call<ArrayList<Cart>> call = APIUtils.getData().getListCart(token);
            call.enqueue(new Callback<ArrayList<Cart>>() {
                @Override
                public void onResponse(Call<ArrayList<Cart>> call, Response<ArrayList<Cart>> response) {
                    if(response.isSuccessful()){
                        getView().listCart(response.body());
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
}
