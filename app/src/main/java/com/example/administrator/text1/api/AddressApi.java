package com.example.administrator.text1.api;

import com.example.administrator.text1.model.IndexModel;
import com.example.administrator.text1.model.address.AddressDetailsModel;
import com.example.administrator.text1.model.address.AreaListModel;
import com.example.administrator.text1.utils.http.HttpClient;
import com.example.administrator.text1.utils.http.RestCallback;
import com.example.administrator.text1.utils.serves.AddressServes;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 功能描述：Retrofit：网络请求便捷开发框架的应用
 * Created by hzhm on 2016/8/17.
 */
public class AddressApi {
    private static Retrofit retrofit;
    private static AddressServes serves;

    //实例化Retrofit对象
    public static void init() {
        retrofit = new Retrofit.Builder()
                .client(HttpClient.getInstance())
                .baseUrl("http://app.trc.com/trcapi/v1/")
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //实例化AddressServes对象/这里采用的是Java的动态代理模式
        serves = retrofit.create(AddressServes.class);
    }

    /**
     * 获取省市区列表
     *
     * @param callback
     */
    public static void getProvinceList(RestCallback<AreaListModel> callback) {
        //调用方法发送请求（enqueue：表示实现异步请求）
        serves.getProvinceList("").enqueue(callback);
    }

    /**
     * 获取收货地址详情
     *
     * @param callback
     */
    public static void getAddressDetails(RestCallback<AddressDetailsModel> callback) {
        serves.getAddresssDetails("291").enqueue(callback);
    }

    /**
     * 添加收获地址
     * @param name
     * @param phone
     * @param isDefault
     * @param province
     * @param city
     * @param district
     * @param address
     * @param idNumber
     * @param front
     * @param back
     * @param callback
     */
    public static void addAddress(final String name, final String phone, final String isDefault, final String province,
                                  final String city, final String district, final String address, final String idNumber, final byte[] front,
                                  final byte[] back, RestCallback<Void> callback) {
        //指定相关参数的contentType，即请求的参数类型(注：MediaType.parse("image/*")，以二进制传输过来的图片，给其申明为“image/*"即指定传输的内容类型为图片)
        MediaType mediaType = MediaType.parse("text/plain");
        serves.addAddress(RequestBody.create(mediaType, name),
                RequestBody.create(mediaType, phone),
                RequestBody.create(mediaType, isDefault),
                RequestBody.create(mediaType, province),
                RequestBody.create(mediaType, city),
                RequestBody.create(mediaType, district),
                RequestBody.create(mediaType, address),
                RequestBody.create(mediaType, idNumber),
                RequestBody.create(MediaType.parse("image/*"), front),
                RequestBody.create(MediaType.parse("image/*"), back)).enqueue(callback);
    }

    public static void getIndexList(RestCallback<IndexModel> callback){
        serves.getIndexList().enqueue(callback);
    }
}