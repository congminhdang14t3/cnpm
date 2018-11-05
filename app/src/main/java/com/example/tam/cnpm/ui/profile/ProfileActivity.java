package com.example.tam.cnpm.ui.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.response.User;
import com.example.tam.cnpm.ui.login.LoginActivity_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_profile)
public class ProfileActivity extends BaseActivity<ProfilePresenterImpl> implements ProfileContract.ProfileView{

    @ViewById(R.id.edit_fname_profile)
    EditText mEditFirstName;

    @ViewById(R.id.edit_lname_profile)
    EditText mEditLastName;

    @ViewById(R.id.text_email_profile)
    TextView mTextEmail;


    @Override
    protected void initPresenter() {
        mPresenter = new ProfilePresenterImpl(this);
    }

    @Override
    protected void afterView() {
        mPresenter.setLogIn();
    }

    public void editProfileOnClick(View view) {
        mPresenter.editProfile(mEditFirstName.getText().toString(),mEditLastName.getText().toString());
    }

    public void logOutProfileOnClick(View view) {
        mPresenter.logOut();
    }

    @Override
    public void setProfile(User user) {
        mEditFirstName.setText(user.getFirstName());
        mEditLastName.setText(user.getLastName());
        mTextEmail.setText(user.getEmail());
    }

    @Override
    public void changeActivity() {
        LoginActivity_.intent(this)
                .start();
        finish();
    }
}
