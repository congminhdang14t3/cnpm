package com.example.tam.cnpm.ui.cart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.ui.list_product.ListProductAdapter;
import com.example.tam.cnpm.ui.payment.PaymentActivity_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@EActivity(R.layout.activity_cart)
public class CartActivity extends BaseActivity<CartPresenterImpl> implements CartContract.CartView {

    @ViewById(R.id.recycler_view_cart)
    RecyclerView mRecyclerView;

    static CartAdapter mAdapter;

    @ViewById(R.id.tvTotalPriceCart)
    static TextView mTextTotalPriceCart;

    static int mTotalPrice;

    public static ArrayList<Cart> mList = new ArrayList<>();
    @Override
    protected void initPresenter() {
        mPresenter = new CartPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        mAdapter = new CartAdapter(this,mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getListCart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    public void btnCountinueBuy_onclick(View view) {
        finish();
    }
    public static void changeTotal(){
        int total = 0;
        for (int i=0;i<mList.size();i++){
            total+=mList.get(i).getProduct().getPrice()*mList.get(i).getQuantity();
        }
        mTextTotalPriceCart.setText("Total: "+total+"đ");
        mTotalPrice = total;

    }

    @Override
    public void listCart(ArrayList<Cart> response) {
        mList.clear();
        mList.addAll(response);
        mAdapter.notifyDataSetChanged();
        changeTotal();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    public void goToPaymentActivity(View view) {
     mPresenter.createPaymentJson(mList,mTotalPrice);
    }

    public static void deleteAllCart(){
        mList.clear();
        mAdapter.notifyDataSetChanged();
    }
}
