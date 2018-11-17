package com.example.tam.cnpm.ui.payment;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.response.MessageResponse;
import com.example.tam.cnpm.service.response.User;
import com.example.tam.cnpm.service.retrofit2.APIUtils;
import com.example.tam.cnpm.service.retrofit2.DataClient;
import com.example.tam.cnpm.service.retrofit2.RetroClient;
import com.example.tam.cnpm.ui.cart.CartActivity;
import com.example.tam.cnpm.ui.web_view.WebViewActivity_;
import com.example.tam.cnpm.ulti.Ulti;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentPresenterImpl extends BasePresenter<PaymentContract.PaymentView> implements PaymentContract.PaymentPresenter {
    public PaymentPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void handlePayment(String json, String money, String fname, String lname, String phone, String address, int id) {
        if (fname.trim().equals("") ||
                lname.trim().equals("") ||
                phone.trim().equals("") ||
                address.trim().equals("")) {
            getView().showToast("Please fill full imformation!!");
            return;
        }

        SharedPreferences sharedPreferences = getContext().
                getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.FNAME,fname);
        editor.putString(Constant.LNAME,lname);
        editor.putString(Constant.PHONE,phone);
        editor.putString(Constant.ADDRESS,address);
        editor.apply();

        String jsonLink = "";
        try {
            JSONObject obj = new JSONObject(json);
            obj.put(Constant.PHONE, phone);
            obj.put(Constant.FNAME, fname);
            obj.put(Constant.LNAME, lname);
            obj.put(Constant.ADDRESS, address);
            System.out.println(obj.toString());
            jsonLink = obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (id) {
            case R.id.radio_ship_code:
                getView().showLoading();
                Call<MessageResponse> call =
                        RetroClient.getClient("http://52.14.71.211/api/").create(DataClient.class).orderShipCode(Ulti.getToken(getContext()),
                                jsonLink);
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if (response.isSuccessful()) {
                            CartActivity.deleteAllCart();
                            getView().finishActivity();
                        } else {
                            getView().showErrorConnect();
                        }
                        getView().dismissLoading();
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        getView().showError(t.toString());
                        getView().dismissLoading();
                    }
                });
                break;
            case R.id.radio_paypal:
                getView().showLoading();
                Call<String> stringCall = APIUtils.getData().getLink(money);
                final String finalJsonLink = jsonLink;
                stringCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            String link = response.body();
                            WebViewActivity_.intent(getContext())
                                    .link(link)
                                    .jsonLink(finalJsonLink)
                                    .start();
                            getView().finishActivity();
                        } else {
                            getView().showErrorConnect();
                        }
                        getView().dismissLoading();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        getView().showError(t.toString());
                        getView().dismissLoading();
                    }
                });
                break;
            default:
        }
    }

    @Override
    public void initPayment() {
        SharedPreferences sharedPreferences = getContext().
                getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        User user = new User();
        user.setFirstName(sharedPreferences.getString(Constant.FNAME,""));
        user.setLastName(sharedPreferences.getString(Constant.LNAME,""));
        user.setPhone(sharedPreferences.getString(Constant.PHONE,""));
        user.setAddress(sharedPreferences.getString(Constant.ADDRESS,""));

        getView().initPayment(user);
    }
}
