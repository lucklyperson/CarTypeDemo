package com.example.wu.cartypedemo;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by $wu on 2017-08-09 上午 9:21.
 * 创建单例获取RetrofitService
 */

class RetrofitServiceUtil {

    private static RetrofitServiceUtil instance;
    private OkHttpClient client;

    private RetrofitServiceUtil() {
        //okHttpClient的初始化
        client = new OkHttpClient();
        client = client.newBuilder()
                .addInterceptor(new HeaderInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public static RetrofitServiceUtil getInstance() {
        if (instance == null) {
            synchronized (RetrofitServiceUtil.class) {
                if (instance == null) {
                    instance = new RetrofitServiceUtil();
                }
            }
        }
        return instance;

    }

    public static RetrofitService getRetrofitServiceWithHeader() {
        return getInstance().innGetRetrofitServiceWithHeader();
    }


    private RetrofitService innGetRetrofitServiceWithHeader() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.shaodianbao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(RetrofitService.class);
    }


}
