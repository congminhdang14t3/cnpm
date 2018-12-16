package com.example.tam.cnpm.ui.payment;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.service.response.Cart;
import com.example.tam.cnpm.service.response.User;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import static com.example.tam.cnpm.Constant.JSON_PAYMENT;
import static com.example.tam.cnpm.Constant.LIST_CART_EXTRAS;
import static com.example.tam.cnpm.Constant.MONEY;

@EActivity(R.layout.activity_payment)
public class PaymentActivity extends BaseActivity<PaymentPresenterImpl> implements PaymentContract.PaymentView {
    @ViewById(R.id.edit_fname_payment)
    EditText editFirstName;

    @ViewById(R.id.edit_lname_payment)
    EditText editLastName;

    @ViewById(R.id.edit_phone_payment)
    EditText editPhone;

    @ViewById(R.id.edit_address_payment)
    EditText editAddress;

    @ViewById(R.id.group_payment)
    RadioGroup group;

    @Extra(JSON_PAYMENT)
    String jsonPayment;

    @Extra(LIST_CART_EXTRAS)
    ArrayList<Cart> mListCart;

    @Override
    protected void initPresenter() {
        mPresenter = new PaymentPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        setTitle("payment");
        mPresenter.initPayment();
    }

    public void paymentOnClick(View view) {
        switch (view.getId()) {
            case R.id.button_cancel_payment:
                finish();
                break;
            case R.id.button_ok_payment:
                mPresenter.handlePayment(jsonPayment, editFirstName.getText().toString(),
                        editLastName.getText().toString(), editPhone.getText().toString(),
                        editAddress.getText().toString(), group.getCheckedRadioButtonId(), mListCart);
                break;
            default:
                break;
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void initPayment(User user) {
        editFirstName.setText(user.getFirstName());
        editLastName.setText(user.getLastName());
        editPhone.setText(user.getPhone());
        editAddress.setText(user.getAddress());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.initPayment();
    }
}
