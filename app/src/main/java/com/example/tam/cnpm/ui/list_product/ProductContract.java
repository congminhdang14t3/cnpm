package com.example.tam.cnpm.ui.list_product;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.response.Product;

import java.util.ArrayList;

public interface ProductContract {
    interface ProductView extends BaseView {
        void listProduct(ArrayList<Product>response);
        void filter(String text);
    }

    interface ProductPresenter{
        void getListProduct(int categoryId);
    }
}
