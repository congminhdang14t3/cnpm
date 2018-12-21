package com.example.tam.cnpm.ui;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.os.Handler;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.ui.cart.CartActivity_;
import com.example.tam.cnpm.ui.list_product.ProductActivity_;
import com.example.tam.cnpm.ui.login.LoginActivity_;
import com.example.tam.cnpm.ui.news.NewsActivity_;
import com.example.tam.cnpm.ui.order.OrderActivity_;
import com.example.tam.cnpm.ui.product_category.CategoryActivity_;
import com.example.tam.cnpm.ui.profile.ProfileActivity_;
import com.example.tam.cnpm.ulti.SharedPrefs;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById(R.id.boom_menu)
    BoomMenuButton mBoomMenuButton;

    @ViewById(R.id.linear_main_activity)
    LinearLayout mLinearMain;

    @ViewById(R.id.linear_splash)
    LinearLayout mLinearSplash;

    @ViewById(R.id.text_version)
    TextView mTextViewVersion;

    @ViewById(R.id.text_copy_right)
    TextView mTextViewCopyRight;

    @ViewById(R.id.image_logo)
    ImageView mImageLogoHrm;

    int[] listImage = {R.drawable.drug, R.drawable.cart, R.drawable.ic_news, R.drawable.account
            , R.drawable.ic_order, R.drawable.store};
    String[] listText;
    String token;

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void afterView() {
        mActionBar.hide();
        animationSplash();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLinearSplash.setVisibility(View.GONE);
                mActionBar.show();
            }
        }, 3500);

        setTitle("Drug Store");
        SharedPrefs.init(this);
        token = SharedPrefs.getInstance().get(Constant.TOKEN, String.class);

        mActionBar.setDisplayHomeAsUpEnabled(false);
        listText = getResources().getStringArray(R.array.listBoomMenu);
        for (int i = 0; i < mBoomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index) {
                                case 0:
                                    CategoryActivity_.intent(MainActivity.this)
                                            .start();
                                    break;
                                case 1:
                                    CartActivity_.intent(MainActivity.this).start();
                                    break;
                                case 2:
                                    NewsActivity_.intent(MainActivity.this)
                                            .start();
                                    break;
                                case 3:
                                    gotoProfile();
                                    break;
                                case 4:
                                    gotoOrder();
                                    break;
                                case 5:
                                    showAboutUs();
                                    break;
                                default:
                                    break;

                            }
                        }
                    })
                    .normalImageRes(listImage[i])
                    .normalText(listText[i])
                    .normalTextColor(Color.WHITE)
                    .textGravity(Gravity.CENTER)
                    .textSize(15);
            mBoomMenuButton.setNormalColor(Color.WHITE);
            mBoomMenuButton.addBuilder(builder);
        }
        mBoomMenuButton.setNormalColor(Color.parseColor("#1BA8FF"));
        mBoomMenuButton.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {

            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {
            }

            @Override
            public void onBoomDidHide() {
                mLinearMain.setVisibility(View.VISIBLE);
            }

            @Override
            public void onBoomWillShow() {
                mLinearMain.setVisibility(View.GONE);
            }

            @Override
            public void onBoomDidShow() {

            }
        });
    }

    @Click
    void text_not_remember_drug() {
        CategoryActivity_.intent(MainActivity.this)
                .start();
    }

    @Click
    void text_want_to_buy() {
        ProductActivity_.intent(MainActivity.this)
                .categoryId(0)
                .start();
    }

    void gotoProfile() {
        if (token.equals("")) {
            SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("You don't have account, you need to login")
                    .setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            LoginActivity_.intent(MainActivity.this).start();
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    });
            dialog.setCancelable(false);
            dialog.show();
        } else {
            ProfileActivity_.intent(MainActivity.this).start();
        }
    }

    void gotoOrder() {
        if (token.equals("")) {
            showToast("You don't have orders");
        } else {
            OrderActivity_.intent(this)
                    .start();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        token = SharedPrefs.getInstance().get(Constant.TOKEN, String.class);
    }

    private void showAboutUs() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.title_about_us))
                .setMessage(getString(R.string.message_about_us))
                .create().show();
    }

    private void animationSplash() {
        Animation animMove_to_right = AnimationUtils.loadAnimation(this, R.anim.anim_move_to_right);
        Animation animUptoDown = AnimationUtils.loadAnimation(this, R.anim.anim_up_to_down);
        mTextViewVersion.startAnimation(animMove_to_right);
        mTextViewCopyRight.startAnimation(animMove_to_right);
        mImageLogoHrm.startAnimation(animUptoDown);
    }

}
