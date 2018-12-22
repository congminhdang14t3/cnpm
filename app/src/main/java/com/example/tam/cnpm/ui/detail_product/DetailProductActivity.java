package com.example.tam.cnpm.ui.detail_product;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.response.FeedBack;
import com.example.tam.cnpm.service.response.Product;
import com.example.tam.cnpm.ui.login.LoginActivity_;
import com.example.tam.cnpm.ui.store.StoreActivity_;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import github.chenupt.springindicator.SpringIndicator;

import static com.example.tam.cnpm.Constant.STORE_EXTRAS;
import static com.example.tam.cnpm.ulti.Ulti.changeMoneyIntToString;

@EActivity(R.layout.activity_detail_product)
public class DetailProductActivity extends BaseActivity<DetailProductPresenterImpl> implements DetailProductContract.DetailProductView, View.OnClickListener {
    @Extra
    Product mProduct;

    @ViewById(R.id.text_drug_name)
    TextView mTextDrugName;

    @ViewById(R.id.text_drug_store)
    TextView mTextDrugStore;

    @ViewById(R.id.viewpager_detail_product)
    ViewPager mViewPagerDetailProduct;

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

    @ViewById(R.id.linear_comment_empty)
    LinearLayout mLinearCommentEmpty;

    @ViewById(R.id.linear_buy_product)
    LinearLayout mLinearBuyProduct;

    @ViewById(R.id.indicator)
    SpringIndicator indicator;

    FeedBackAdapter mAdapter;

    ArrayList<FeedBack> mList = new ArrayList<>();

    @Override
    protected void initPresenter() {
        mPresenter = new DetailProductPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        System.out.println(mProduct.toString());
        setTitle(getString(R.string.detail_product));
        if (mProduct.getCountInStock() == 0) {
            mLinearBuyProduct.setVisibility(View.GONE);
        }
        mTextDrugName.setText(mProduct.getName());

        ImagePagerAdapter adapter = new ImagePagerAdapter(this, mProduct.getPicture());
        mViewPagerDetailProduct.setAdapter(adapter);
        indicator.setViewPager(mViewPagerDetailProduct);

        mExpandableTextView.setText(mProduct.getDetail());
        mTextPriceProduct.setText(changeMoneyIntToString(mProduct.getPrice()) + " đ");
        //set up recyclerview
        mAdapter = new FeedBackAdapter(this, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mTextDrugStore.setOnClickListener(this);

        mPresenter.setLogIn();
        mPresenter.getListFeedback(mProduct.getId());
        mPresenter.getStoreName(mProduct.getStores());
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
                if (count < mProduct.getCountInStock()) {
                    mEditCountProduct.setText((count + 1) + "");
                } else {
                    showToast(getString(R.string.no_more_product));
                }
                break;

            default:
                break;
        }
    }

    public void handling(View view) {
        switch (view.getId()) {
            case R.id.text_add_to_cart:
                String count = mEditCountProduct.getText().toString();
                int quantity = Integer.parseInt(count);
                mPresenter.addToCart(mProduct, quantity);
                break;

            default:
                break;

        }
    }

    @Override
    public void listFeedBack(ArrayList<FeedBack> response) {
        if (response.isEmpty()) {
            mLinearCommentEmpty.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            if (mRecyclerView.getVisibility() == View.GONE) {
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
        mEditFeedback.setText("");
        mRatingBar.setRating(0);
        mList.clear();
        mList.addAll(response);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void logIn(boolean isLogIn) {
        if (isLogIn) {
            mLinearComment.setVisibility(View.VISIBLE);
        } else {
            mTextGotoLoginPAge.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getStoreName(String store) {
        mTextDrugStore.setText(">>> " + store);
    }

    public void getLogInActivity(View view) {
        LoginActivity_.intent(this).start();
        finish();
    }

    public void sendFeedbackOnlick(View view) {
        mPresenter.addFeedBack(mProduct, mEditFeedback.getText().toString(), (int) mRatingBar.getRating());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_drug_store:
                StoreActivity_.intent(this)
                        .extra(STORE_EXTRAS, mProduct.getStores())
                        .start();
                break;
        }
    }
}
