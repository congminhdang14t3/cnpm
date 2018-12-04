package com.example.tam.cnpm.base;

public interface BaseView {
    void showLoading();

    void dismissLoading();

    void showError(String message);

    void showMessage(String message);

    void showErrorConnect();

    void showToast(String message);

    void showSweetDialog(String message, int type);

    void setTitle(String title);

    String getStringResource(int resource);
}
