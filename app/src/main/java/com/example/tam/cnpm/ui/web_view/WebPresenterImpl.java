package com.example.tam.cnpm.ui.web_view;

import android.content.Context;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.response.MessageResponse;
import com.example.tam.cnpm.service.retrofit2.APIUtils;
import com.example.tam.cnpm.service.retrofit2.DataClient;
import com.example.tam.cnpm.service.retrofit2.RetroClient;
import com.example.tam.cnpm.ui.cart.CartActivity;
import com.example.tam.cnpm.ulti.SharedPrefs;
import com.example.tam.cnpm.ulti.Ulti;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tam.cnpm.Constant.TOKEN;

public class WebPresenterImpl extends BasePresenter<WebContract.WebView> implements WebContract.WebPresenter {
    public WebPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void handleUrl(String link) {
        System.out.println(link);
        String[] arr = link.split("=");
        String list = arr[1].split("&")[0].replaceAll("%27", "\"");
        String token = arr[2].split("&")[0];
        String id = arr[3];
        System.out.println(list);
        System.out.println(token);
        System.out.println(id);
        try {
            JSONObject obj = new JSONObject();
            obj.put(TOKEN, token);
            obj.put(Constant.PAYERID, id);
            obj.put(Constant.LIST_ORDER_CODE, "AAAA");
            String send = obj.toString().replace("\"AAAA\"", list);
            System.out.println(send);
            getView().showLoading();
            Call<MessageResponse> call =
                    RetroClient.getClient("http://52.14.71.211/api/")
                            .create(DataClient.class).orderPayment(send);
            call.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if (response.isSuccessful()) {
                        getView().showToast("payment success");
                        CartActivity.deleteAllCart();
                        getView().finishActivity();
                    } else {
                        System.out.println(response.toString());
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
        } catch (JSONException e) {
            e.printStackTrace();
            getView().showErrorConnect();
        }
    }
}
