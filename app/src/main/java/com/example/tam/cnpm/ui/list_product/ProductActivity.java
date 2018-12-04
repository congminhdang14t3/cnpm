package com.example.tam.cnpm.ui.list_product;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.base.BaseAdapter;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.ui.detail_product.DetailProductActivity_;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_product)
public class ProductActivity extends BaseActivity<ProductPresenterImpl> implements ProductContract.ProductView, BaseAdapter.OnItemClickListener {

    @Extra
    int categoryId;

    @ViewById(R.id.recycler_view_search_product)
    RecyclerView mRecyclerView;

    ListProductAdapter mAdapter;

    ArrayList<Product> mList;

    ArrayList<Product> itemsCopy;

    @Override
    protected void initPresenter() {
        mPresenter = new ProductPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        setTitle("Product");
        mList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAdapter = new ListProductAdapter(this,mList,this);
        mRecyclerView.setAdapter(mAdapter);
        //load data
        mPresenter.getListProduct(categoryId);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_view,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem searchViewMenuItem = menu.findItem(R.id.search_view);
        final SearchView searchView = (SearchView) searchViewMenuItem.getActionView();
        //searchView.setIconified(false);
        searchView.setQueryHint(getStringResource(R.string.search_hint));
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals("")){
                    filter(s);
                }else {
                    if(s.length()>=2){
                       filter(s);
                    }
                }
                return true;
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void listProduct(ArrayList<Product> response) {
        itemsCopy = new ArrayList<>();
        itemsCopy.addAll(response);
        mList.clear();
        mList.addAll(response);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        String image = mList.get(position).getPicture().isEmpty()? "" : mList.get(position).getPicture().get(0).getImage();
        DetailProductActivity_.intent(ProductActivity.this)
                .mProduct(mList.get(position))
                .mImageProduct(image)
                .start();
    }
    @Override
    public void filter(String text) {
        mList.clear();
        if(text.isEmpty()){
            mList.addAll(itemsCopy);
        } else{
            text = text.toLowerCase();
            for(Product item: itemsCopy){
                if(item.getName().toLowerCase().contains(text)){
                    mList.add(item);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
