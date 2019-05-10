package com.example.administrator.coolstock.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sendOkHttpRequest(String adress,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(2,TimeUnit.SECONDS)
                .readTimeout(300,TimeUnit.SECONDS)
                .writeTimeout(300,TimeUnit.SECONDS)
                .build();

        Request request=new Request.Builder().url(adress).build();

        client.newCall(request).enqueue(callback);
    }
}
