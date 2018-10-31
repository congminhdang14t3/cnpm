package com.example.tam.cnpm.ui.news;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.response.DocBao;
import com.example.tam.cnpm.service.response.Product;

import java.util.ArrayList;

public interface NewsContract {
    interface NewsView extends BaseView {
        void getListNews(ArrayList<DocBao> response);
    }

    interface NewsPresenter{
        void getListNews();
    }
}
