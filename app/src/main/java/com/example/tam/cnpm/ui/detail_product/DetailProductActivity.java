package com.example.tam.cnpm.ui.detail_product;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.response.Product;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_detail_product)
public class DetailProductActivity extends BaseActivity {
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

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void afterView() {
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mTextDrugName.setText(getString(R.string.name)+": "+mProduct.getName());
        mTextExpireDate.setText(getString(R.string.expire_date)+": "+mProduct.getExpireDate());
        Picasso.get()
                .load(mImageProduct)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.errorimage)
                .into(mImageDetailProduct);
        mExpandableTextView.setText(mProduct.getDetail());
        mTextPriceProduct.setText(mProduct.getPrice()+"đ");
    }

    public void changeCountProduct(View view) {
        int count = Integer.parseInt(mEditCountProduct.getText().toString());

        switch (view.getId()){
            case R.id.button_sub:
                if(count>1){
                    mEditCountProduct.setText((count-1)+"");
                }
                break;
            case R.id.button_add:
                mEditCountProduct.setText((count+1)+"");break;
            default:break;
        }
    }
}
