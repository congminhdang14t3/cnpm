package com.example.tam.cnpm.ui.payment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.MessageResponse;
import com.example.tam.cnpm.service.response.User;
import com.example.tam.cnpm.service.retrofit2.APIUtils;
import com.example.tam.cnpm.service.retrofit2.DataClient;
import com.example.tam.cnpm.service.retrofit2.RetroClient;
import com.example.tam.cnpm.ui.cart.CartActivity;
import com.example.tam.cnpm.ui.login.LoginActivity_;
import com.example.tam.cnpm.ui.web_view.WebViewActivity_;
import com.example.tam.cnpm.ulti.SharedPrefs;
import com.example.tam.cnpm.ulti.Ulti;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tam.cnpm.Constant.TOKEN;

public class PaymentPresenterImpl extends BasePresenter<PaymentContract.PaymentView> implements PaymentContract.PaymentPresenter {
    public PaymentPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void handlePayment(final String json, final String fname, final String lname, final String phone, final String address, final int id
            , final ArrayList<Cart> list) {
        String token = SharedPrefs.getInstance().get(TOKEN, String.class);
        if (fname.trim().equals("") ||
                lname.trim().equals("") ||
                phone.trim().equals("") ||
                address.trim().equals("")) {
            getView().showSweetDialog(getContext().getString(R.string.fill_full_infor), SweetAlertDialog.ERROR_TYPE);
            return;
        }
        if (phone.length() != 10) {
            getView().showSweetDialog(getContext().getString(R.string.phone_not_right), SweetAlertDialog.ERROR_TYPE);
            return;
        }
        if (token.equals("")) {
            SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getContext().getString(R.string.are_you_sure))
                    .setContentText(getContext().getString(R.string.want_to_login))
                    .setConfirmButton(getContext().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            LoginActivity_.intent(getContext()).start();
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .setCancelButton(getContext().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            payment(json, fname, lname, phone, address, id, list);
                            sweetAlertDialog.dismiss();
                        }
                    });
            dialog.setCancelable(false);
            dialog.show();
        } else {
            payment(json, fname, lname, phone, address, id, list);
        }

    }

    @Override
    public void initPayment() {
        SharedPreferences sharedPreferences = getContext().
                getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        User user = new User();
        user.setFirstName(sharedPreferences.getString(Constant.FNAME, ""));
        user.setLastName(sharedPreferences.getString(Constant.LNAME, ""));
        user.setPhone(sharedPreferences.getString(Constant.PHONE, ""));
        user.setAddress(sharedPreferences.getString(Constant.ADDRESS, ""));

        getView().initPayment(user);
    }

    private void payment(String json, String fname, String lname, String phone, String address, int id, final ArrayList<Cart> list) {
        SharedPreferences sharedPreferences = getContext().
                getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constant.FNAME, fname);
        editor.putString(Constant.LNAME, lname);
        editor.putString(Constant.ADDRESS, address);
        editor.putString(Constant.PHONE, phone);
        editor.apply();
        String jsonLink = "";
        try {
            JSONObject obj = new JSONObject(json);
            obj.put(Constant.FNAME, fname);
            obj.put(Constant.LNAME, lname);
            obj.put(Constant.ADDRESS, address);
            obj.put(Constant.PHONE, phone);
            jsonLink = obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        String token = SharedPrefs.getInstance().get(TOKEN, String.class);
        switch (id) {
            case R.id.radio_ship_code:
                getView().showLoading();
                Call<MessageResponse> call;

                if (token.equals("")) {
                    call = RetroClient.getClient("http://52.14.71.211/api/")
                            .create(DataClient.class).orderShipCodeNotToken(jsonLink);
                } else {
                    call = RetroClient.getClient("http://52.14.71.211/api/")
                            .create(DataClient.class).orderShipCode(token, jsonLink);
                }
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if (response.isSuccessful()) {
                            CartActivity.deleteAllCart();
                            getView().showToast(getContext().getString(R.string.payment_success));
                            getView().finishActivity();
                        } else {
                            if (response.code() == 400) {
                                try {
                                    showErrorPayment(response.errorBody().string(), list);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
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
                Call<String> stringCall;
                if (token.equals("")) {
                    stringCall = RetroClient.getClient("http://52.14.71.211/api/")
                            .create(DataClient.class)
                            .redirectNotToken(jsonLink);
                } else {
                    stringCall = RetroClient.getClient("http://52.14.71.211/api/")
                            .create(DataClient.class)
                            .redirect(token, jsonLink);
                }

                stringCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            String link = response.body().replaceAll("\"", "");
                            WebViewActivity_.intent(getContext())
                                    .link(link)
                                    .start();
                            getView().finishActivity();
                        } else {
                            if (response.code() == 400) {
                                try {
                                    showErrorPayment(response.errorBody().string(), list);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
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

    private String showCartError(String e, ArrayList<Cart> list) {
        String[] error = e.replace("[", "").replace("]", "").split(",");
        String er = "";
        for (Cart cart : list) {
            for (String i : error) {
                if (Integer.parseInt(i) == cart.getProduct().getId()) {
                    er += "\"" + cart.getProduct().getName() + "\" " + getContext().getString(R.string.not_enough)
                            + " " + cart.getQuantity() + " "+getContext().getString(R.string.product_in_stock)+"\n";
                    break;
                }
            }
        }
        return er;
    }

    private void showErrorPayment(String error, ArrayList<Cart> list) {
        try {
            JSONObject object = new JSONObject(error);
            SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setContentText(showCartError(object.get("fields").toString(), list))
                    .setConfirmButton(getContext().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            getView().finishActivity();
                            sweetAlertDialog.dismiss();
                        }
                    });
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
