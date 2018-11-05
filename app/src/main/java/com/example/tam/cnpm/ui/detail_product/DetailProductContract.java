package com.example.tam.cnpm.ui.detail_product;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.response.Product;

import java.util.ArrayList;

public interface DetailProductContract {
    interface DetailProductView extends BaseView {
    }

    interface DetailProductPresenter{
        void addToCart(Product product,int quantity);
    }
}
