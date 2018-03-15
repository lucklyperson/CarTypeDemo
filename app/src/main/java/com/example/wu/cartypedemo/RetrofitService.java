package com.example.wu.cartypedemo;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wu on 2018/3/15.
 */

public interface RetrofitService {

    @GET("api.php?con=car&act=brand")
    Observable<MainActivity.Result<Car>> getCarBrandList();

    /**
     * 汽车类型
     *
     * @param id
     */
    @GET("api.php?con=car&act=type")
    Observable<MainActivity.Result<Car>> getCarTypeById(@Query("id") String id);

}
