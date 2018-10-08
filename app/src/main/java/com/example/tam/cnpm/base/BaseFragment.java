package com.example.tam.cnpm.base;


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.example.tam.cnpm.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {

    protected final String TAG = this.getClass().getSimpleName();

    protected T mPresenter;

    protected abstract void initPresenter();

    protected abstract void afterView();

    private ProgressDialog mProgressDialog;

    @AfterViews
    protected void initView(){this.afterView();}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            View decor = getActivity().getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        initPresenter();

        if(mPresenter != null){
            mPresenter.attachView(this);
        }

        initProgressDialog();
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getStringResource(R.string.loading));
        mProgressDialog.setCancelable(false);
    }

    public void dismissProgressDialog(){
        if(mProgressDialog.isShowing() && mProgressDialog!=null && !getActivity().isFinishing()){
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog(){
        if(!mProgressDialog.isShowing() && mProgressDialog!=null && !getActivity().isFinishing()){
            mProgressDialog.show();
        }
    }

    protected void showAlertDialog(String message){
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton(getStringResource(R.string.ok),null)
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
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDestroyView() {
        if(mPresenter != null){
            mPresenter.detachView();
        }
        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        super.onDestroyView();
    }

}
