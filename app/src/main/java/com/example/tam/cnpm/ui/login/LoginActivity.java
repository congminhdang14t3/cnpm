package com.example.tam.cnpm.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.ui.MainActivity_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static com.example.tam.cnpm.Constant.REQUEST_CODE_LIBRARY;

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
        initPermission();
    }

    public void skipOnclick(View view) {
        finish();

    }

    public void textRegisterOnlcick(View view) {
        mPresenter.showDialogRegister();
    }


    @Override
    public void resultSignIn(int status) {
        switch (status) {
            case 0:
                showToast("Login Fail!");
                break;
            case 1:
                showToast("Login Success!");
                finish();
                break;

        }
    }

    @Override
    public void showImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_LIBRARY);
    }

    public void signInOnlick(View view) {
        mPresenter.signIn(mEditEmail.getText().toString(), mEditPass.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LIBRARY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            mPresenter.setImage(uri);
        }
    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Kiểm tra quyền đọc/ghi dữ liệu vào thiết bị lưu trữ ngoài.
            int readPermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (writePermission != PackageManager.PERMISSION_GRANTED ||
                    readPermission != PackageManager.PERMISSION_GRANTED) {

                // Nếu không có quyền, cần nhắc người dùng cho phép.
                this.requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        1
                );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();
            }
            // Hủy bỏ hoặc bị từ chối.
            else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
