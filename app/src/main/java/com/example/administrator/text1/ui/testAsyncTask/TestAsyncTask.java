package com.example.administrator.text1.ui.testAsyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.example.administrator.text1.R;
import com.example.administrator.text1.model.NewSBean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：实现异步加载功能的两种方式、数据解析
 * 第一种（1）：使用AsyncTask实现异步加载
 * 第二种（2）：开启新线程实现异步加载
 *   三  （3）：网络数据的解析（InputStream--String）、Json数据的解析（String--JsonObject--JsonArray--NewsBean）
 * (使用异步加载的条件：一般涉及到网络及其他耗时操作时用到)
 * Created by hzhm on 2016/7/8.
 */
public class TestAsyncTask extends Activity {

    private static final String URL = "http://www.imooc.com/api/teacher?type=4&num=30";
    private List<NewSBean> newSBeanList = new ArrayList<NewSBean>();
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_asynctask);

        mListView = (ListView) findViewById(R.id.listview);
        //1.1、执行异步操作
        new NewsAsyncTask().execute(URL);
    }

    /**
     * 实现网络获取数据的异步访问类（注：<String, Void, List<NewSBean>这三个参数：String:传入的数据源网址；Void:中间过程；List<NewSBean>：执行成功后返回的对象）
     */
    class NewsAsyncTask extends AsyncTask<String, Void, List<NewSBean>> {

        //1.2、执行异步操作doInBackground:执行相关的耗操作
        @Override
        protected List<NewSBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        //1.3、onPostExecute：执行成功后回调的方法，一般进行UI线程的更新操作
        @Override
        protected void onPostExecute(List<NewSBean> list) {
            super.onPostExecute(list);
            NewsAdapter mAdapter = new NewsAdapter(TestAsyncTask.this,newSBeanList,mListView);
            mListView.setAdapter(mAdapter);
        }
    }

    /**
     * 3.2、将Json格式数据转化为我们所封装的NewSBean对象（String--JsonObject--JsonArray--NewsBean）
     * @param url
     * @return
     */
    private List<NewSBean> getJsonData(String url){
        List<NewSBean> newsBeanList = new ArrayList<NewSBean>();
        try {
            //new URL(url).openStream()：连接网络获取InputStream网络数据
            String jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject;
            NewSBean newSBean;
            Log.e("json",jsonString);

            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                newSBean = new NewSBean();
                newSBean.newsIconUrl = jsonObject.getString("picSmall");
                newSBean.newsTitle = jsonObject.getString("name");
                newSBean.newsContent = jsonObject.getString("description");
                newSBeanList.add(newSBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsBeanList;
    }

    /**
     * 3.1、通过InputStream解析网页返回的数据（InputStream--String）
     * @param is
     * @return
     */
    private String readStream(InputStream is){
        InputStreamReader isr;
        String result = "";

        try {
            String line = "";
            isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null){
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
