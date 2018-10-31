package com.example.tam.cnpm.ui.news;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tam.cnpm.base.BasePresenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tam.cnpm.service.response.DocBao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class NewsPresenterImpl extends BasePresenter<NewsContract.NewsView> implements NewsContract.NewsPresenter {
    public NewsPresenterImpl(Context context) {
        super(context);
    }


    @Override
    public void getListNews() {
        getView().showLoading();
        String url = "https://vnexpress.net/rss/suc-khoe.rss";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<DocBao>list = new ArrayList<>();
                        Document document = Jsoup.parse(response);
                        Elements elements = document.select("item");
                        for (Element element : elements){
                            Element elementTitle = element.getElementsByTag("title").first();
                            Element elementDes = element.getElementsByTag("description").first();
                            Element elementLink = element.getElementsByTag("guid").first();
                            String image = Jsoup.parse(elementDes.text()).select("img").get(0).attr("src");
                            if(!image.endsWith(".jpg")){
                                image = Jsoup.parse(elementDes.text()).select("img").get(0).attr("data-original");
                            }
                            list.add(new DocBao(elementTitle.text(),image,elementLink.text()));
                        }
                        getView().getListNews(list);
                        getView().dismissLoading();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(stringRequest);
    }
}
