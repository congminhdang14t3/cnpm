package com.example.tam.cnpm.ui.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.response.User;
import com.example.tam.cnpm.service.retrofit2.APIUtils;
import com.example.tam.cnpm.ui.cart.CartActivity;
import com.example.tam.cnpm.ui.login.LoginActivity_;
import com.example.tam.cnpm.ulti.SharedPrefs;
import com.example.tam.cnpm.ulti.Ulti;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tam.cnpm.Constant.TOKEN;

public class ProfilePresenterImpl extends BasePresenter<ProfileContract.ProfileView> implements ProfileContract.ProfilePresenter {
    public ProfilePresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void logOut() {
        new AlertDialog.Builder(getContext())
                .setMessage("Do you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CartActivity.mList.clear();
                        SharedPrefs.getInstance().put(TOKEN,"");
                        getView().changeActivity();
                    }
                })
                .setNegativeButton("No", null)
                .create().show();
    }

    @Override
    public void setLogIn() {
        String token = SharedPrefs.getInstance().get(TOKEN, String.class);
        getView().showLoading();
        Call<User> call = APIUtils.getData().getProfile(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    SharedPreferences sharedPreferences = getContext().
                            getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constant.FNAME, user.getFirstName());
                    editor.putString(Constant.LNAME, user.getLastName());
                    editor.apply();
                    getView().setProfile(user);
                } else {
                    getView().showErrorConnect();
                }
                getView().dismissLoading();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                getView().showError(t.toString());
                getView().dismissLoading();
            }
        });
    }

    @Override
    public void editProfile(final String fname, final String lname, final Uri uri) {
        new AlertDialog.Builder(getContext())
                .setMessage("Would you like to edit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getView().showLoading();
                        String token = SharedPrefs.getInstance().get(TOKEN, String.class);
                        RequestBody first = RequestBody.create(MediaType.parse("text/plain"), fname);
                        RequestBody last = RequestBody.create(MediaType.parse("text/plain"), lname);
                        Call<User> call;
                        if (uri == null) {
                            call = APIUtils.getData().editProfileNotAvatar(token, first, last);
                        } else {
                            //multipart/form-data
                            File file = new File(Ulti.getPath(getContext(), uri));
                            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            MultipartBody.Part body =
                                    MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
                            call = APIUtils.getData().editProfile(token, first, last, body);
                        }
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    getView().setProfile(response.body());
                                } else {
                                    System.out.println(response.message());
                                    getView().showErrorConnect();
                                }
                                getView().dismissLoading();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                getView().showError(t.toString());
                                getView().dismissLoading();
                            }
                        });
                    }
                })
                .setNegativeButton("No", null)
                .create().show();
    }
}
