package com.example.administrator.text1.utils.serves;

import com.example.administrator.text1.model.IndexModel;
import com.example.administrator.text1.model.NewVersionInfoModel;
import com.example.administrator.text1.model.address.AddressDetailsModel;
import com.example.administrator.text1.model.address.AddressListModel;
import com.example.administrator.text1.model.address.AreaListModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 功能描述：相关注解的使用及其相关说明
 * 注：Retrofit需要定义一个接口，用来返回我们的Call对象，实例：Get：
 * Created by hzhm on 2016/8/17.
 */
public interface AddressServes {

    ///--------定义具体的请求接口

    /**
     * 获取收货地址列表
     * @return
     */
    @GET("addresses")
    Call<AddressListModel> getAddreessList();

    /**
     * 添加收货地址
     * @param name
     * @param phone
     * @param default2
     * @param province
     * @param city
     * @param district
     * @param address
     * @param idNumber
     * @param front
     * @param back
     * @return
     */
    ///---- @Multipart：Content_Type注解一般与其配套使用的参数注解为@Part
    @Multipart
    @POST("address")
    Call<Void> addAddress(@Part("name") RequestBody name, @Part("phone") RequestBody phone, @Part("default") RequestBody default2,
                          @Part("province") RequestBody province, @Part("city") RequestBody city, @Part("district") RequestBody district,
                          @Part("address") RequestBody address, @Part("idNumber") RequestBody idNumber,
                          @Part("front\"; filename=\"front.jpg") RequestBody  front, @Part("back\"; filename=\"back.jpg") RequestBody back);

    /**
     * 修改收货地址
     * @param name
     * @param phone
     * @param isDefaut
     * @param province
     * @param city
     * @param district
     * @param address
     * @param addressId
     * @return
     */
    ///---- @FormUrlEncoded：Content_Type注解一般与其配套使用的参数注解为@Field，但如果有占位符的字段，也会用到@Path参数注解
    @FormUrlEncoded
    @PUT("address/{addressId}")
    Call<Void> updateAddress(@Field("name") String name, @Field("phone") String phone, @Field("default") int isDefaut,
                             @Field("province") String province, @Field("city") String city, @Field("district") String district,
                             @Field("address") String address, @Path("addressId") String addressId);


    ///---- @GET：一般get请求的参数注解有两种：一种是@Path,为占位符注解；另一种是@Query，键值对注解
    @GET("address/{addressId}")
    Call<Void> deleteAddress(@Path("addressId") String addressId);

    /**
     * 获取省市区列表
     * @param etag
     * @return
     */
    @GET("address/areas")
    Call<AreaListModel> getProvinceList(@Query("etag") String etag);

    /**
     * 获取收货地址详情
     * @param addressId
     * @return
     */
    @GET("address/{addressId}")
    Call<AddressDetailsModel> getAddresssDetails(@Path("addressId") String addressId);

    @GET("brands")
    Call<IndexModel> getIndexList();

    /**
     * 版本更新
     *
     * @return
     */
    @GET("operation/apk/{platformId}")
    Call<NewVersionInfoModel> getVersionUpdater(@Path("platformId") String addressId);
}
