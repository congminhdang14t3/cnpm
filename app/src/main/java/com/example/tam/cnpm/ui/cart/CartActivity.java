package com.example.tam.cnpm.ui.cart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.response.Cart;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.tam.cnpm.ulti.Ulti.changeMoneyIntToString;

@EActivity(R.layout.activity_cart)
public class CartActivity extends BaseActivity<CartPresenterImpl> implements CartContract.CartView {

    @ViewById(R.id.recycler_view_cart)
    RecyclerView mRecyclerView;

    static CartAdapter mAdapter;

    @ViewById(R.id.tvTotalPriceCart)
    static TextView mTextTotalPriceCart;

    @ViewById(R.id.linear_cart_empty)
    LinearLayout mLinearEmpty;

    public static ArrayList<Cart> mList = new ArrayList<>();

    @Override
    protected void initPresenter() {
        mPresenter = new CartPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        setTitle(getString(R.string.cart));
        mAdapter = new CartAdapter(this, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getListCart();
        changeTotal();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_order:
                mPresenter.goOrderActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void changeTotal() {
        int total = 0;
        for (int i = 0; i < mList.size(); i++) {
            total += mList.get(i).getProduct().getPrice() * mList.get(i).getQuantity();
        }
        mTextTotalPriceCart.setText(changeMoneyIntToString(total) + " đ");
    }

    @Override
    public void listCart(ArrayList<Cart> response) {
        mList.clear();
        mList.addAll(response);
        mAdapter.notifyDataSetChanged();
        changeTotal();
        if (mList.isEmpty()) {
            mLinearEmpty.setVisibility(View.VISIBLE);
        } else {
            mLinearEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    public void goToPaymentActivity(View view) {
        mPresenter.createPaymentJson(mList);
    }

    public static void deleteAllCart() {
        mList.clear();
        mAdapter.notifyDataSetChanged();
        changeTotal();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mAdapter.notifyDataSetChanged();
        changeTotal();
        if (mList.isEmpty()) {
            mLinearEmpty.setVisibility(View.VISIBLE);
        } else {
            mLinearEmpty.setVisibility(View.GONE);
        }
    }
}
