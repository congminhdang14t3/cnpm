package com.example.tam.cnpm.ui.web_view;

import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tam.cnpm.R;
import com.example.tam.cnpm.base.BaseActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_web)
public class WebViewActivity extends BaseActivity<WebPresenterImpl> implements WebContract.WebView {
    @ViewById(R.id.web_view)
    WebView webView;

    @Extra
    String link;
    @Override
    protected void initPresenter() {
        mPresenter = new WebPresenterImpl(this);
    }

    @Override
    protected void afterView() {
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(url.startsWith("http://localhost:3000")){
                    webView.setVisibility(View.GONE);
                    mPresenter.handleUrl(url);
                }
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
