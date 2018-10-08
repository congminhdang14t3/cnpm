package com.example.tam.cnpm.base;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BasePresenter<T> {
    protected final String CALENDAR_FORMAT_DAY = "yyyy-MM-dd";
    protected final String CALENDAR_FORMAT_HOUR = "HH:mm:ss";
    protected final String RESULT_RESPONSE = "Updated";
    protected T view;

    protected Context context;

    public BasePresenter(Context context){
        this.context = context;
    }

    protected void attachView(T view){
        this.view = view;
    };

    protected void detachView(){
        this.view = null;
    };

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getDateByFormat(String format, Date date){
        return new SimpleDateFormat(format).format(date);
    }
}
