package com.example.tam.cnpm.ui.web_view;

import com.example.tam.cnpm.base.BaseView;

public interface WebContract {
    interface WebView extends BaseView {
        void finishActivity();
    }

    interface WebPresenter {
        void handleUrl(String link,String json);
    }
}
