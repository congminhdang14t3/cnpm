package com.example.tam.cnpm.ui.cart;

import android.content.Context;

import com.example.tam.cnpm.base.BasePresenter;

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

    }
}
