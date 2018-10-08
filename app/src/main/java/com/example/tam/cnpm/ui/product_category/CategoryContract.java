package com.example.tam.cnpm.ui.product_category;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.a.response.CategoryResponse;

import java.util.ArrayList;

public interface CategoryContract {
    interface CategoryView extends BaseView {
        void getListCategory(ArrayList<String> responses);
    }

    interface CategoryPresenter{
        void getListCategory();
    }
}
