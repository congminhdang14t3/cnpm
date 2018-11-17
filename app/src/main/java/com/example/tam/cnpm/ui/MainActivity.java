package com.example.tam.cnpm.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;

import com.example.tam.cnpm.Constant;
import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.ui.cart.CartActivity;
import com.example.tam.cnpm.ui.list_product.ProductActivity_;
import com.example.tam.cnpm.ui.login.LoginActivity_;
import com.example.tam.cnpm.ui.news.NewsActivity_;
import com.example.tam.cnpm.ui.product_category.CategoryActivity_;
import com.example.tam.cnpm.ui.profile.ProfileActivity_;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById(R.id.boom_menu)
    BoomMenuButton mBoomMenuButton;
    int[]listImage = {R.drawable.home,R.drawable.drug,R.drawable.cart,R.drawable.ic_news,R.drawable.account
            ,R.drawable.information,R.drawable.store};
    String[]listText;
    @Override
    protected void initPresenter() {

    }

    @Override
    protected void afterView() {
        mActionBar.setDisplayHomeAsUpEnabled(false);
        listText = getResources().getStringArray(R.array.listBoomMenu);
        for (int i = 0; i < mBoomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index){
                                case 1:
                                    CategoryActivity_.intent(MainActivity.this)
                                            .start();break;
                                case 3:
                                    NewsActivity_.intent(MainActivity.this)
                                            .start();break;
                                case 4:
                                    ProfileActivity_.intent(MainActivity.this)
                                            .start();
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
    }

    @Click
    void text_not_remember_drug(){
        CategoryActivity_.intent(MainActivity.this)
                .start();
    }

    @Click
    void text_want_to_buy(){
        ProductActivity_.intent(MainActivity.this)
                    .categoryId(0)
                .start();
    }
}
