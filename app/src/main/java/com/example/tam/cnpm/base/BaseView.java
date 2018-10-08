package com.example.tam.cnpm.base;

public interface BaseView {
    void showLoading();

    void dismissLoading();

    void showError(String message);

    void showMessage(String message);

    void showErrorConnect();

    void showToast(String message);

    String getStringResource(int resource);
}
