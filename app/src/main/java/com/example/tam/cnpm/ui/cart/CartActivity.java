package com.example.tam.cnpm.ui.cart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.ui.list_product.ListProductAdapter;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_cart)
public class CartActivity extends BaseActivity {
    @ViewById(R.id.recycler_view_cart)
    RecyclerView mRecyclerView;
    CartAdapter mAdapter;
    ArrayList<Cart> mList;
    @Override
    protected void initPresenter() {
        mPresenter = new CartPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        Product product = new Product();
        product.setPrice(10000);
        product.setName("ABC");
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(2);
        mList = new ArrayList<>();
        mList.add(cart);
        mAdapter = new CartAdapter(this,mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    public void paymentOnclick(View view) {
        Product product = new Product();
        product.setPrice(10000);
        product.setName("ABC");
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(2);
        mList.add(0,cart);
        mAdapter.notifyItemInserted(0);
    }
}
