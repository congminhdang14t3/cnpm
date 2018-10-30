package com.example.tam.cnpm.ui.cart;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.response.Cart;

import java.util.ArrayList;

public interface CartContract {
    interface CartView extends BaseView {
        void listCart(ArrayList<Cart> response);
    }

    interface CartPresenter{
        void getListCart();
    }
}
