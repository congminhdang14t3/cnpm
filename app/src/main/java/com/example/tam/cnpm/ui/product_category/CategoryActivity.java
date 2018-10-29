package com.example.tam.cnpm.ui.product_category;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.a.response.CategoryResponse;
import com.example.tam.cnpm.ui.list_product.ProductActivity_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_category)
public class CategoryActivity extends BaseActivity<CategoryPresenterImpl> implements CategoryContract.CategoryView,
ListView.OnItemClickListener{

    @ViewById(R.id.list_view_category)
    ListView mListViewCategory;

    ArrayAdapter<String>mAdapter;

    ArrayList<String>mListCategoryName;

    ArrayList<CategoryResponse>mListCategory;

    @Override
    protected void initPresenter() {
        mPresenter = new CategoryPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mListCategoryName = new ArrayList<>();
        mListCategory = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mListCategoryName);
        mListViewCategory.setAdapter(mAdapter);
        mListViewCategory.setOnItemClickListener(this);
        //get list data
        mPresenter.getListCategory();
    }
    @Override
    public void getListCategory(ArrayList<CategoryResponse> responses) {
        mListCategoryName.clear();
        for(CategoryResponse cate : responses){
            mListCategoryName.add(cate.getName());
        }
        mListCategory.clear();
        mListCategory.addAll(responses);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ProductActivity_.intent(CategoryActivity.this)
                .categoryId(mListCategory.get(i).getId())
                .start();
    }
}
