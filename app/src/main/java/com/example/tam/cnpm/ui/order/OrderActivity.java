package com.example.tam.cnpm.ui.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.response.Order;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_order)
public class OrderActivity extends BaseActivity<OrderPresenterImpl> implements OrderContract.OrderView {

    @ViewById(R.id.recycle_view_order)
    RecyclerView mRecyclerView;
    OrderAdapter mAdapter;
    ArrayList<Order> mList;

    @Override
    protected void initPresenter() {
        mPresenter = new OrderPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        setTitle("Order");
        mList = new ArrayList<>();
        mAdapter = new OrderAdapter(this, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getListOrder();
    }

    @Override
    public void listOrder(ArrayList<Order> response) {
        mList.clear();
        mList.addAll(response);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

}
