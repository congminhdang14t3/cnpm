package com.example.tam.cnpm.ui.payment;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.DocBao;
import com.example.tam.cnpm.service.response.User;

import java.util.ArrayList;

public interface PaymentContract {
    interface PaymentView extends BaseView {
        void finishActivity();

        void initPayment(User user);
    }

    interface PaymentPresenter {
        void handlePayment(String json, String fname, String lname, String phone, String address, int id, ArrayList<Cart> list);

        void initPayment();
    }
}
