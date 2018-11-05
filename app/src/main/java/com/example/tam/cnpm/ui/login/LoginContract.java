package com.example.tam.cnpm.ui.login;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.response.DocBao;

import java.util.ArrayList;

public interface LoginContract {
    interface LoginView extends BaseView {
        void registerStatus(String status);
        void resultSignIn(int status);
    }

    interface LoginPresenter{
        void showDialogRegister();
        void signIn(String username,String password);
        void setSignIn();
    }
}
