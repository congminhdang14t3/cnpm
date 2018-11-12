package com.example.tam.cnpm.ui.login;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.response.DocBao;

import java.util.ArrayList;

public interface LoginContract {
    interface LoginView extends BaseView {
        void registerStatus(String status);
        void resultSignIn(int status);
        void showImage();
    }

    interface LoginPresenter{
        void showDialogRegister();
        void signIn(String username,String password);
        void setSignIn();
        void setImage(Uri uri);
    }
}
