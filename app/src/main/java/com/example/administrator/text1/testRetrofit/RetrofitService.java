package com.example.administrator.text1.testRetrofit;

import com.example.administrator.text1.model.birthday.DateMode;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author HuangMing on 2018/11/13.
 */

public interface RetrofitService {

    @GET("query")
    Observable<DateMode>  getInfo(@Query("type") String type, @Query("postid") String postid);
}
