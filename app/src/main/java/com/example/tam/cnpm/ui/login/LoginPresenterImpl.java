package com.example.tam.cnpm.ui.login;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.example.tam.cnpm.ulti.Ulti;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImpl extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {
    public LoginPresenterImpl(Context context) {
        super(context);
    }
    CircleImageView image;
    static String path="";
    @Override
    public void showDialogRegister() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_register);
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final EditText email = dialog.findViewById(R.id.edit_email_register);
        final EditText pass = dialog.findViewById(R.id.edit_pass_register);
        EditText pass2 = dialog.findViewById(R.id.edit_pass2_register);
        final EditText firstName = dialog.findViewById(R.id.edit_first_name_register);
        final EditText lastName = dialog.findViewById(R.id.edit_last_name_register);
        Button cancel = dialog.findViewById(R.id.button_cancel_register);
        Button register = dialog.findViewById(R.id.button_register);
        image = dialog.findViewById(R.id.image_register);
        image.setImageBitmap(BitmapFactory.decodeFile
                (new File("/storage/6633-6433/DCIM/Camera/20181104_174113.jpg").getAbsolutePath()));
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getView().showImage();
            }
        });
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
//                Call<User> call = APIUtils.getData().getUser(email.getText().toString(),
//                        pass.getText().toString(),
//                        firstName.getText().toString(),
//                        lastName.getText().toString(),
//                        "",
//                        "customer");
                RequestBody textEmail = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());
                RequestBody textPass = RequestBody.create(MediaType.parse("text/plain"), pass.getText().toString());
                RequestBody textFirst = RequestBody.create(MediaType.parse("text/plain"), firstName.getText().toString());
                RequestBody textLast = RequestBody.create(MediaType.parse("text/plain"), lastName.getText().toString());
                File file =  new File(path);
                //multipart/form-data
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
                RequestBody textCustomer = RequestBody.create(MediaType.parse("text/plain"), "customer");
                Call<User> call = APIUtils.getData().getUser1(textEmail,
                        textPass,textFirst,textLast,body,textCustomer);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()){
                            getView().registerStatus("Register Success!");
                        }else{
                            System.out.println("Fail onResponse: "+response.message());
                            getView().registerStatus("Register Fail!");
                        }
                        getView().dismissLoading();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        getView().registerStatus("Register Fail!");
                        System.out.println("onFailure: "+t.toString());
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
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constant.TOKEN,"Token "+response.body().getToken());
                        editor.apply();
                        getView().resultSignIn(1);
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

    @Override
    public void setImage(Uri uri) {
        path = Ulti.getPath(getContext(),uri);
        try {
            InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            image.setImageBitmap(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
