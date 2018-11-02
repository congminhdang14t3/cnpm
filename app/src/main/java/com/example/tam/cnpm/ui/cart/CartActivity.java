package com.example.tam.cnpm.ui.cart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.ui.list_product.ListProductAdapter;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_cart)
public class CartActivity extends BaseActivity<CartPresenterImpl> implements CartContract.CartView {

    @ViewById(R.id.recycler_view_cart)
    RecyclerView mRecyclerView;

    CartAdapter mAdapter;

    @ViewById(R.id.tvTotalPriceCart)
    static TextView mTextTotalPriceCart;
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
            total+=mList.get(i).getTotalPrice();
        }
        mTextTotalPriceCart.setText("Total: "+total+"đ");
    }

    @Override
    public void listCart(ArrayList<Cart> response) {
        mList.clear();
        mList.addAll(response);
        mAdapter.notifyDataSetChanged();
        changeTotal();
    }
}
