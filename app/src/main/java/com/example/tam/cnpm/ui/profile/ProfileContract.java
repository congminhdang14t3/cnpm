package com.example.tam.cnpm.ui.profile;

import android.net.Uri;

import com.example.tam.cnpm.base.BaseView;
import com.example.tam.cnpm.service.response.User;

public interface ProfileContract {
    interface ProfileView extends BaseView {
        void setProfile(User user);

        void changeActivity();

        void finishActivity();
    }

    interface ProfilePresenter {
        void logOut();

        void setLogIn();

        void editProfile(String fname, String lname, Uri uri);

        void showDialogChangePass();
    }
}
