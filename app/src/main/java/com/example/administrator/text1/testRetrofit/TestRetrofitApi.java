package com.example.administrator.text1.testRetrofit;

import com.example.administrator.text1.model.birthday.DateMode;

import java.util.Date;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author HuangMing on 2018/11/13.
 */

public interface TestRetrofitApi {

    /**
     * Path占位符
     *
     * @param android
     * @param size
     * @param page
     * @return
     */
    @GET("api/data/{Android}/{size}/{page}")
    Call<DateMode> getInfo(@Path("Android") String android, @Path("size") String size, @Path("page") int page);


    /**
     * QueryMap传递一个数组
     *
     * @param map
     * @return
     */
    @GET("api/data")
    Call<DateMode> getInfo(@QueryMap Map<String, String> map);

    /**
     * Query传递一个键值对
     *
     * @param Android
     * @param size
     * @param page
     * @return
     */
    @GET("api/data")
    Call<DateMode> getInfo(@Query("Android") String Android, @Query("size") String size, @Query("page") String page);

    /**
     * Query还能传递一个数组
     *
     * @param Android
     * @return
     */
    @GET("api/data")
    Call<DateMode> getInfo(@Query("Android") String... Android);

    @FormUrlEncoded
    @POST("aa/HelloServlet")
    Call<DateMode> getInfo(@Field("userName") String userName, @Field("passWord") String passWord);

    @Multipart
    @POST("/aa/UploadServlet")
    Call<DateMode> getInfo(@Part MultipartBody.Part file);

    @Multipart
    @POST("/aa/UploadServlet")
    Call<DateMode> getInfo(@Part MultipartBody.Part file, @QueryMap Map<String, String> map);
}
