package com.example.wu.cartypedemo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by $wu on 2017-08-04 上午 10:28.
 * okhttp的拦截器添加header
 */

public class HeaderInterceptor implements Interceptor {
    public HeaderInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("versionnumber", "2.7")
                .build();

        return chain.proceed(request);
    }
}
