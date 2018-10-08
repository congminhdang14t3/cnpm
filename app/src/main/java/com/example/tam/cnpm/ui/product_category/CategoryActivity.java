package com.example.tam.cnpm.ui.product_category;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.a.response.CategoryResponse;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_category)
public class CategoryActivity extends BaseActivity<CategoryPresenterImpl> implements CategoryContract.CategoryView{
    @ViewById(R.id.list_view_category)
    ListView mListViewCategory;
    ArrayAdapter<String>mAdapter;
    ArrayList<String>mListCategory;
    @Override
    protected void initPresenter() {
        mPresenter = new CategoryPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mListCategory = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mListCategory);
        mListViewCategory.setAdapter(mAdapter);
        mPresenter.getListCategory();
    }
    @Override
    public void getListCategory(ArrayList<String> responses) {
        mListCategory.clear();
        mListCategory.addAll(responses);
        mAdapter.notifyDataSetChanged();
    }
}
