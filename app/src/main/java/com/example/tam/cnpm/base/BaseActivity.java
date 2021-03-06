package com.example.tam.cnpm.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.ui.cart.CartActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity
//@WindowFeature(Window.FEATURE_NO_TITLE)
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected final String TAG = this.getClass().getSimpleName();

    private ProgressDialog mProgressDialog;
    protected T mPresenter;
    protected ActionBar mActionBar;

    protected abstract void initPresenter();

    protected abstract void afterView();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
        super.onDestroy();
    }

    @AfterViews
    protected void initView() {
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.show();
        this.afterView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initPresenter();

        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initProgressDialog();
    }

    @Override
    public void setTitle(String title) {
        mActionBar.setTitle(title);
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getStringResource(R.string.loading));
        mProgressDialog.setCancelable(false);
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setIndeterminateDrawable(getResources()
//                .getDrawable(R.drawable.progressbar_handler));
    }

    private void showProgressDialog() {
        if (!mProgressDialog.isShowing() && mProgressDialog != null && !isFinishing()) {
            mProgressDialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialog.isShowing() && mProgressDialog != null && !isFinishing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getStringResource(R.string.ok), null)
                .create().show();
    }

    @Override
    public String getStringResource(int resource) {
        return getResources().getString(resource);
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void dismissLoading() {
        dismissProgressDialog();
    }

    @Override
    public void showError(String message) {
        showAlertDialog(message);
    }

    @Override
    public void showMessage(String message) {
        showAlertDialog(message);
    }

    @Override
    public void showErrorConnect() {
        showAlertDialog(getStringResource(R.string.no_connection));
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSweetDialog(String message, int type) {
        SweetAlertDialog dialog = new SweetAlertDialog(this, type)
                .setTitleText(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //do whatever
                onBackPressed();
                return true;
            case R.id.menu_cart:
                CartActivity_.intent(this)
                        .start();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
