package com.example.tam.cnpm.ui.store;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.response.Store;

import java.util.ArrayList;

public interface StoreContract {
    interface StoreView extends BaseView {
        void init(Store store);
    }

    interface StorePresenter{
        void getStoreById(int id);
    }
}
