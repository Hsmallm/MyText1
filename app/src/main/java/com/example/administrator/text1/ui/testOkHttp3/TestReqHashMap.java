package com.example.administrator.text1.ui.testOkHttp3;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.example.administrator.text1.R;
import com.example.administrator.text1.model.IndexListModel;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by hzhm on 2016/11/11.
 *
 * Json数据的解析及其model的转换
 *  //再根据请求的数据形式进行相应的Model转化
        //------数据实例：1、"groups":[{"name":"pic","brands":[{"name":"v","url":"h28"},...
        //------分析：这里是将"groups":[...]整体转化为model
                IndexListModel model = new Gson().fromJson(responseString,IndexListModel.class);
        //------数据实例：2、"data":{"canInvestLoanCount":14,"sysDate":1479624971359,...
        //------分析：这里是将data对应的{...}里面的数据转化为model，所有得将请求数据转化为相应的JSONObject，在通过他拿到相应的数据进行转换
 J              SONObject jsonObject = null;
                jsonObject = new JSONObject(responseString);
                String groups = jsonObject.getString("data");
                new Gson().fromJson(groups,IndexItemsModel.class);
 */

public class TestReqHashMap extends Activity {

    private static final String URL= "http://app.trc.com/trcapi/v1/brands";
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footer_view);

        client = new OkHttpClient();
        req();
    }

    private void req() {
        Request request = new Request.Builder().url(URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("---onFailure---",e+"");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    //请求成功后，得到响应体数据
                    ResponseBody responseBody = response.body();
                    String responseString = responseBody.string();
                    try {
                        //再根据请求的数据形式进行相应的Model转化
                        //------数据实例：1、"groups":[{"name":"pic","brands":[{"name":"v","url":"h28"},...
                        //------分析：这里是将"groups":[...]整体转化为model
                        IndexListModel model = new Gson().fromJson(responseString,IndexListModel.class);
                        //------数据实例：2、"data":{"canInvestLoanCount":14,"sysDate":1479624971359,...
                        //------分析：这里是将data对应的{...}里面的数据转化为model，所有得将请求数据转化为相应的JSONObject，在通过他拿到相应的数据进行转换
                        JSONObject jsonObject = null;
                        jsonObject = new JSONObject(responseString);
//                        String groups = jsonObject.getString("data");
//                        new Gson().fromJson(groups,IndexItemsModel.class);
                        String name = model.groups.get(0).name;
                        Log.e("---onResponse---",responseString);
                        Log.e("---name---",name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.e("---onFailure---","服务器异常！！！");
                }
            }
        });
    }
}
