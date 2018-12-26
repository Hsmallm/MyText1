package com.example.administrator.text1.testVolley;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.text1.R;

import org.json.JSONObject;

import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HuangMing on 2018/11/7.
 *         Des:在2013年Google I/O大会上推出了一个新的网络通信框架——Volley。
 *         Volley可是说是把AsyncHttpClient和Universal-Image-Loader的优点集于了一身，
 *         既可以像AsyncHttpClient一样非常简单地进行HTTP通信，也可以像Universal-Image-Loader一样轻松加载网络上的图片。
 *         除了简单易用之外，Volley在性能方面也进行了大幅度的调整，它的设计目标就是非常适合去进行数据量不大，但通信频繁的网络操作，
 *         而对于大数据量的网络操作，比如说下载文件等，Volley的表现就会非常糟糕
 */

public class TestVolleyActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGet;
    private Button btnChange;
    private TextView tvContributor;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mvc_main);

        btnGet = (Button) findViewById(R.id.get);
        btnChange = (Button) findViewById(R.id.change);
        tvContributor = (TextView) findViewById(R.id.top_contributor);

        mQueue = Volley.newRequestQueue(this);
        btnGet.setOnClickListener(this);
        btnChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                reqByJson();
                break;
            case R.id.change:
                reqByString();
                break;
            default:
                break;
        }
    }

    /**
     * 请求一段字符串数据
     */
    private void reqByString() {
        StringRequest stringRequest = new StringRequest("https://www.baidu.com/", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                tvContributor.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tvContributor.setText(volleyError.getMessage());
            }
        });
        mQueue.add(stringRequest);
    }

    private void req() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            /**
             * 添加请求参数的方法
             * @return
             * @throws AuthFailureError
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("params1", "value1");
                map.put("params2", "value2");
                return map;
            }
        };
    }

    /**
     * 请求一段JSON数据
     */
    private void reqByJson() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://appmanager.trc.com/api/rest/v/uca92a90d09a107a20-2.7.0-official-android", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                tvContributor.setText(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tvContributor.setText(volleyError.getMessage());
            }
        });
        mQueue.add(jsonObjectRequest);
    }
}
