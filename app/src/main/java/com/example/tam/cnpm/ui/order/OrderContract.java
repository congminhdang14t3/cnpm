package com.example.tam.cnpm.ui.order;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.response.Order;

import java.util.ArrayList;

public interface OrderContract {
    interface OrderView extends BaseView {
        void listOrder(ArrayList<Order>response);
    }

    interface OrderPresenter{
        void getListOrder();
    }
}
