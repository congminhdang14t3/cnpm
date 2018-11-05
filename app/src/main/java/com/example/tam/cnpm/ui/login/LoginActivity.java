package com.example.tam.cnpm.ui.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.ui.MainActivity_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity<LoginPresenterImpl> implements LoginContract.LoginView {

    @ViewById(R.id.edit_email_login)
    EditText mEditEmail;

    @ViewById(R.id.edit_pass_login)
    EditText mEditPass;

    @Override
    protected void initPresenter() {
        mPresenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        mPresenter.setSignIn();
    }

    public void skipOnclick(View view) {
        MainActivity_.intent(this)
                .start();
        finish();

    }

    public void textRegisterOnlcick(View view) {
        mPresenter.showDialogRegister();
    }

    @Override
    public void registerStatus(String status) {
        showToast(status);
    }

    @Override
    public void resultSignIn(int status) {
        switch (status){
            case 0:
                showToast("Login Fail!");break;
            case 1:
                showToast("Login Success!");
                MainActivity_.intent(this)
                        .start();
                finish();
                break;

        }
    }

    public void signInOnlick(View view) {
        mPresenter.signIn(mEditEmail.getText().toString(),mEditPass.getText().toString());
    }
}
