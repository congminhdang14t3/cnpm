package com.example.tam.cnpm.service.retrofit2;
public class APIUtils {
    //https://congminhdang14t3vn.000webhostapp.com/appbanhang/
    public static final String baseUrl = "http://52.14.71.211/api/";
    //public static final String baseUrl = "https://hrm.dev.codecomplete.asia/api/";

    private static DataClient sInstance;

    public static DataClient getData(){
        if(sInstance == null){
            sInstance = RetrofitClient.getClient(baseUrl).create(DataClient.class);
        }
        return sInstance;
    }
}
