package com.example.tam.cnpm.ui.login;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.response.TokenResponse;
import com.example.tam.cnpm.service.response.User;
import com.example.tam.cnpm.service.retrofit2.APIUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImpl extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {
    public LoginPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void showDialogRegister() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_register);
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        final EditText email = dialog.findViewById(R.id.edit_email_register);
        final EditText pass = dialog.findViewById(R.id.edit_pass_register);
        EditText pass2 = dialog.findViewById(R.id.edit_pass2_register);
        final EditText firstName = dialog.findViewById(R.id.edit_first_name_register);
        final EditText lastName = dialog.findViewById(R.id.edit_last_name_register);
        Button cancel = dialog.findViewById(R.id.button_cancel_register);
        Button register = dialog.findViewById(R.id.button_register);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getView().showLoading();
                Call<User> call = APIUtils.getData().getUser(email.getText().toString(),
                        pass.getText().toString(),
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        "",
                        "customer");
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()){
                            getView().registerStatus("Register Success!");
                        }else{
                            getView().registerStatus("Register Fail!");
                        }
                        getView().dismissLoading();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        getView().registerStatus("Register Fail!");
                        getView().dismissLoading();
                    }
                });
                dialog.dismiss();
            }
        });
    }

    @Override
    public void signIn(String username, String password) {
        getView().showLoading();
        Call<TokenResponse> call = APIUtils.getData().getToken(username,password);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()){
                    if(response.body().getToken() != null){
                        System.out.println(response.body().getToken());
                        getView().resultSignIn(1);
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constant.TOKEN,"Token "+response.body().getToken());
                        editor.apply();
                    }else{
                        getView().resultSignIn(0);
                    }
                }else{
                    getView().resultSignIn(0);
                }
                getView().dismissLoading();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                getView().resultSignIn(0);
            }
        });
    }

    @Override
    public void setSignIn() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Constant.TOKEN,"");
        if(!token.equals("")){
            getView().resultSignIn(1);
        }
    }
}
