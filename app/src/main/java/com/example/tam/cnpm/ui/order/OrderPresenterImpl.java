package com.example.tam.cnpm.ui.order;

import android.content.Context;

import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.response.Order;
import com.example.tam.cnpm.service.retrofit2.APIUtils;
import com.example.tam.cnpm.ulti.SharedPrefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tam.cnpm.Constant.TOKEN;

public class OrderPresenterImpl extends BasePresenter<OrderContract.OrderView> implements OrderContract.OrderPresenter {
    public OrderPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void getListOrder() {
        getView().showLoading();

        String token = SharedPrefs.getInstance().get(TOKEN, String.class);
        Call<ArrayList<Order>> call = APIUtils.getData().listOrders(token);
        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if (response.isSuccessful()) {
                    getView().listOrder(response.body());
                } else {
                    getView().showError(response.toString());
                }
                getView().dismissLoading();
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                getView().showError(t.toString());
                getView().dismissLoading();
            }
        });
    }
}
