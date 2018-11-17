package com.example.tam.cnpm.ui.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.ListView;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.response.DocBao;
import com.example.tam.cnpm.ui.list_product.ListProductAdapter;
import com.example.tam.cnpm.ui.web_view.WebViewActivity_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_new)
public class NewsActivity extends BaseActivity<NewsPresenterImpl> implements NewsContract.NewsView,
ListNewsAdapter.OnItemClickListener{

    @ViewById(R.id.recycle_view_news)
    RecyclerView mRecyclerView;
    ListNewsAdapter mAdapter;
    ArrayList<DocBao> mList;

    @Override
    protected void initPresenter() {
        mPresenter = new NewsPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        mList = new ArrayList<>();
        mAdapter = new ListNewsAdapter(this,mList,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getListNews();
    }

    @Override
    public void onItemClick(int position) {
        WebViewActivity_.intent(this)
                .link(mList.get(position).getLink())
                .start();
    }

    @Override
    public void getListNews(ArrayList<DocBao> response) {
        mList.clear();
        mList.addAll(response);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
