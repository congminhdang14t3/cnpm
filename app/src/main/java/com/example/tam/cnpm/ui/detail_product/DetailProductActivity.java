package com.example.tam.cnpm.ui.detail_product;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.FeedBack;
import com.example.tam.cnpm.service.response.Picture;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.ui.cart.CartActivity;
import com.example.tam.cnpm.ui.cart.CartActivity_;
import com.example.tam.cnpm.ui.cart.CartAdapter;
import com.example.tam.cnpm.ui.login.LoginActivity_;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_detail_product)
public class DetailProductActivity extends BaseActivity<DetailProductPresenterImpl> implements DetailProductContract.DetailProductView {
    @Extra
    Product mProduct;

    @Extra
    String mImageProduct;


    @ViewById(R.id.text_drug_name)
    TextView mTextDrugName;

    @ViewById(R.id.text_expire_date)
    TextView mTextExpireDate;

    @ViewById(R.id.image_detail_product)
    ImageView mImageDetailProduct;

    @ViewById(R.id.expand_text_view)
    ExpandableTextView mExpandableTextView;

    @ViewById(R.id.text_price_product)
    TextView mTextPriceProduct;

    @ViewById(R.id.edit_count_product)
    EditText mEditCountProduct;

    @ViewById(R.id.recycler_view_feedback)
    RecyclerView mRecyclerView;

    @ViewById(R.id.text_go_to_login_page)
     TextView mTextGotoLoginPAge;

    @ViewById(R.id.linear_comment)
    LinearLayout mLinearComment;

    @ViewById(R.id.rating_bar)
    RatingBar mRatingBar;

    @ViewById(R.id.edit_feedback)
    EditText mEditFeedback;

    FeedBackAdapter mAdapter;

    ArrayList<FeedBack> mList = new ArrayList<>();

    @Override
    protected void initPresenter() {
        mPresenter = new DetailProductPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        mTextDrugName.setText(getString(R.string.name) + ": " + mProduct.getName());
        mTextExpireDate.setText(getString(R.string.expire_date) + ": " + mProduct.getExpireDate());
        Picasso.get()
                .load(mImageProduct)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.errorimage)
                .into(mImageDetailProduct);
        mExpandableTextView.setText(mProduct.getDetail());
        mTextPriceProduct.setText(mProduct.getPrice() + "đ");
        //set up recyclerview
        mAdapter = new FeedBackAdapter(this, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.setLogIn();
        mPresenter.getListFeedback(mProduct.getId());

    }

    public void changeCountProduct(View view) {
        int count = Integer.parseInt(mEditCountProduct.getText().toString());

        switch (view.getId()) {
            case R.id.button_sub:
                if (count > 1) {
                    mEditCountProduct.setText((count - 1) + "");
                }
                break;
            case R.id.button_add:
                mEditCountProduct.setText((count + 1) + "");
                break;

            default:
                break;
        }
    }

    public void handling(View view) {
        switch (view.getId()) {
            case R.id.text_buy_now:

                break;
            case R.id.text_add_to_cart:
                int quantity = Integer.parseInt(mEditCountProduct.getText().toString());
                Picture picture = new Picture();
                picture.setImage(mImageProduct);
                List<Picture> list = new ArrayList<>();
                list.add(picture);
                mProduct.setPicture(list);
                mPresenter.addToCart(mProduct, quantity);
                break;

            default:
                break;

        }
    }

    @Override
    public void listFeedBack(ArrayList<FeedBack> response) {
        mList.clear();
        mList.addAll(response);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void logIn(boolean isLogIn) {
        if(isLogIn){
            mLinearComment.setVisibility(View.VISIBLE);
        }else{
            mTextGotoLoginPAge.setVisibility(View.VISIBLE);
        }
    }

    public void getLogInActivity(View view) {
        LoginActivity_.intent(this).start();
        finish();
    }

    public void sendFeedbackOnlick(View view) {
        mPresenter.addFeedBack(mProduct,mEditFeedback.getText().toString(),(int)mRatingBar.getRating());
        mEditFeedback.setText("");
        mRatingBar.setRating(0);
    }
}
