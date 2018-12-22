package com.example.tam.cnpm.ui.cart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.service.retrofit2.APIUtils;
import com.example.tam.cnpm.ui.login.LoginActivity_;
import com.example.tam.cnpm.ui.payment.PaymentActivity_;
import com.example.tam.cnpm.ulti.SharedPrefs;
import com.example.tam.cnpm.ulti.Ulti;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tam.cnpm.Constant.MONEY;
import static com.example.tam.cnpm.Constant.ONE_USD;
import static com.example.tam.cnpm.Constant.SHARED_PREFERENCES_NAME;
import static com.example.tam.cnpm.Constant.TOKEN;

import com.example.tam.cnpm.ui.order.OrderActivity_;
public class CartPresenterImpl extends BasePresenter<CartContract.CartView> implements CartContract.CartPresenter {
    public CartPresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void getListCart() {
        //6daa91518b9f93ca4a4e97d03074c0d917b26b71
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Constant.TOKEN, "");
        if (!token.equals("")) {
            getView().showLoading();

            Call<ArrayList<Cart>> call = APIUtils.getData().getListCart(token);
            call.enqueue(new Callback<ArrayList<Cart>>() {
                @Override
                public void onResponse(Call<ArrayList<Cart>> call, Response<ArrayList<Cart>> response) {
                    if (response.isSuccessful()) {
                        getView().listCart(response.body());
                    }
                    getView().dismissLoading();
                }

                @Override
                public void onFailure(Call<ArrayList<Cart>> call, Throwable t) {
                    getView().showError(t.toString());
                    getView().dismissLoading();
                }
            });
        }

    }

    @Override
    public void createPaymentJson(ArrayList<Cart> list) {
        if (list.isEmpty()) {
            getView().showToast(getContext().getString(R.string.get_cart_empty));
            return;
        }
        ArrayList<Integer> storeId = getListStoreId(list);

        try {
            JSONObject paramObject = new JSONObject();
            JSONObject productObject = new JSONObject();
            for (int i = 0; i < storeId.size(); i++) {
                JSONArray array = new JSONArray();
                for (int j = 0; j < list.size(); j++) {
                    Product product = list.get(j).getProduct();
                    if (product.getStores() == storeId.get(i)) {
                        JSONObject o = new JSONObject();
                        o.put(Constant.PRODUCT_ID, product.getId());
                        o.put(Constant.QUANTITY, list.get(j).getQuantity());
                        array.put(o);
                    }
                }
                productObject.put(storeId.get(i) + "", array);
            }
            paramObject.put(Constant.PRODUCT, productObject);
            PaymentActivity_.intent(getContext())
                    .extra(Constant.JSON_PAYMENT, paramObject.toString())
                    .extra(Constant.LIST_CART_EXTRAS,list)
                    .start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goOrderActivity() {
        String token = SharedPrefs.getInstance().get(TOKEN,String.class);
        if(token.equals("")){
            getView().showToast(getContext().getString(R.string.have_no_order));
        }
        else{
            OrderActivity_.intent(getContext())
                    .start();
        }
    }

    ArrayList<Integer> getListStoreId(ArrayList<Cart> mList) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            int storeid = mList.get(i).getProduct().getStores();
            if (!list.contains(storeid)) {
                list.add(storeid);
            }
        }
        return list;
    }
}
