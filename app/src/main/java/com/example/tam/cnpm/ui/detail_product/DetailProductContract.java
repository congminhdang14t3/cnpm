package com.example.tam.cnpm.ui.detail_product;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.response.FeedBack;
import com.example.tam.cnpm.service.response.Product;

import java.util.ArrayList;

public interface DetailProductContract {
    interface DetailProductView extends BaseView {
        void listFeedBack(ArrayList<FeedBack> response);
        void logIn(boolean isLogIn);
    }

    interface DetailProductPresenter {
        void addToCart(Product product, int quantity);

        void getListFeedback(int id);

        void setLogIn();

        void addFeedBack(Product product, String detail, int star);
    }
}
