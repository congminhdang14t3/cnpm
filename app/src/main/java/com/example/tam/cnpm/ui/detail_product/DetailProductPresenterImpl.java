package com.example.tam.cnpm.ui.detail_product;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BasePresenter;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.FeedBack;
import com.example.tam.cnpm.service.response.MessageResponse;
import com.example.tam.cnpm.service.response.Picture;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.service.response.Store;
import com.example.tam.cnpm.service.retrofit2.APIUtils;
import com.example.tam.cnpm.ui.cart.CartActivity;
import com.example.tam.cnpm.ulti.SharedPrefs;
import com.example.tam.cnpm.ulti.Ulti;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tam.cnpm.Constant.SHARED_PREFERENCES_NAME;
import static com.example.tam.cnpm.Constant.TOKEN;

public class DetailProductPresenterImpl extends BasePresenter<DetailProductContract.DetailProductView> implements DetailProductContract.DetailProductPresenter {
    ArrayList<FeedBack> mList;

    public DetailProductPresenterImpl(Context context) {
        super(context);
        mList = new ArrayList<>();
    }

    @Override
    public void addToCart(Product mProduct, int quantity) {
        String token = SharedPrefs.getInstance().get(TOKEN, String.class);
        if (!token.equals("")) {
            getView().showLoading();
            Call<MessageResponse> call = APIUtils.getData().modifyCart(token, mProduct.getId(), quantity);
            call.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if (response.isSuccessful()) {
                        getView().showToast("Add to cart!");
                    } else {
                        getView().showError(response.toString());
                    }
                    getView().dismissLoading();
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    getView().showError(t.toString());
                    getView().dismissLoading();
                }
            });
        } else {
            boolean isHasCart = false;
            for (int i = 0; i < CartActivity.mList.size(); i++) {
                Cart cart = CartActivity.mList.get(i);
                if (cart.getProduct().getId() == mProduct.getId()) {
                    isHasCart = true;
                    cart.setQuantity(quantity);
                    break;
                }
            }
            if (!isHasCart) {
                Cart cart = new Cart();
                cart.setProduct(mProduct);
                cart.setQuantity(quantity);
                CartActivity.mList.add(0,cart);
            }
            getView().showToast("Add to cart!");
        }

    }

    @Override
    public void getListFeedback(int id) {
        getView().showLoading();
        Call<ArrayList<FeedBack>> call = APIUtils.getData().getListFeedback(id);
        call.enqueue(new Callback<ArrayList<FeedBack>>() {
            @Override
            public void onResponse(Call<ArrayList<FeedBack>> call, Response<ArrayList<FeedBack>> response) {
                if (response.isSuccessful()) {
                    mList.clear();
                    mList.addAll(response.body());
                    getView().listFeedBack(mList);
                } else {
                    getView().showErrorConnect();
                }
                getView().dismissLoading();

            }

            @Override
            public void onFailure(Call<ArrayList<FeedBack>> call, Throwable t) {
                getView().showError(t.toString());
                getView().dismissLoading();
            }
        });
    }

    @Override
    public void setLogIn() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Constant.TOKEN, "");
        getView().logIn(!token.equals(""));
    }

    @Override
    public void addFeedBack(Product product, String detail, int star) {
        if (detail.equals("") || star == 0) {
            getView().showSweetDialog("You need fill full imformation", SweetAlertDialog.ERROR_TYPE);
            return;
        }
        getView().showLoading();
        Call<FeedBack> call = APIUtils.getData().addFeedback(Ulti.getToken(getContext()),
                product.getId(), product.getStores(), detail.trim(), star);
        call.enqueue(new Callback<FeedBack>() {
            @Override
            public void onResponse(Call<FeedBack> call, Response<FeedBack> response) {
                if (response.isSuccessful()) {
                    mList.add(0, response.body());
                    getView().listFeedBack(mList);
                } else {
                    getView().showErrorConnect();
                }
                getView().dismissLoading();
            }

            @Override
            public void onFailure(Call<FeedBack> call, Throwable t) {
                getView().showError(t.toString());
                getView().dismissLoading();
            }
        });
    }

    @Override
    public void getStoreName(int id) {
        getView().showLoading();
        Call<Store> call = APIUtils.getData().getStore(id);
        call.enqueue(new Callback<Store>() {
            @Override
            public void onResponse(Call<Store> call, Response<Store> response) {
                if (response.isSuccessful()) {
                    getView().getStoreName(response.body().getName());
                } else {
                    getView().showToast(getContext().getString(R.string.error_store_name));
                }
                getView().dismissLoading();
            }

            @Override
            public void onFailure(Call<Store> call, Throwable t) {
                getView().showToast(getContext().getString(R.string.error_store_name));
                getView().dismissLoading();
            }
        });
    }
}
