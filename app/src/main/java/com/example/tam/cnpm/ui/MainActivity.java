package com.example.tam.cnpm.ui;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;
import com.example.tam.cnpm.ui.list_product.ProductActivity;
import com.example.tam.cnpm.ui.list_product.ProductActivity_;
import com.example.tam.cnpm.ui.product_category.CategoryActivity;
import com.example.tam.cnpm.ui.product_category.CategoryActivity_;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById(R.id.boom_menu)
    BoomMenuButton mBoomMenuButton;
    int[]listImage = {R.drawable.home,R.drawable.drug,R.drawable.cart,R.drawable.notification,R.drawable.account
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
