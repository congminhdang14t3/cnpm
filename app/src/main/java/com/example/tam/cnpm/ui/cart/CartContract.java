package com.example.tam.cnpm.ui.cart;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.response.Cart;

import java.util.ArrayList;

public interface CartContract {
    interface CartView extends BaseView {
        void listCart(ArrayList<Cart> response);
        void finishActivity();
    }

    interface CartPresenter{
        void getListCart();
        void createPaymentJson(ArrayList<Cart> list);
        void goOrderActivity();
    }
}
