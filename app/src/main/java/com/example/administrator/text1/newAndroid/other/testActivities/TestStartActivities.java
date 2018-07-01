package com.example.administrator.text1.newAndroid.other.testActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;
import com.example.administrator.text1.newAndroid.other.LogUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author HuangMing on 2018/1/5.
 */

public class TestStartActivities extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_start_activities);

        btn = (Button) findViewById(R.id.btn);
        btn.setText("跳转");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttp();
            }
        });
    }

    private void startActivity() {
        Intent intent1 = new Intent(TestStartActivities.this, TestStartActivities1.class);
        startActivity(intent1);
        Intent intent2 = new Intent(TestStartActivities.this, TestStartActivities2.class);
        startActivity(intent2);
    }

    private void startActivities() {
        Intent intent1 = new Intent(TestStartActivities.this, TestStartActivities1.class);
        Intent intent2 = new Intent(TestStartActivities.this, TestStartActivities2.class);
        Intent intent3 = new Intent(TestStartActivities.this, TestStartActivities3.class);
        Intent[] intents = new Intent[]{intent1, intent2, intent3};
        startActivities(intents);
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://localhost:8008/get_data.xml")
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    parseXMLWithPull(response.toString());
                } catch (IOException e) {
                    try {
                        runUIThread();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void runUIThread(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn.setText("跳转2");
            }
        });
    }

    /**
     * Pull解析Xml数据格式
     *
     * @param xmlData
     */
    private void parseXMLWithPull(String xmlData) {
        try {
            //1、获取XmlPullParserFactory实例
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //2、通过XmlPullParserFactory实例对象，得到一个XmlPullParser对象
            XmlPullParser xmlPullParser = factory.newPullParser();
            //3、调用xmlPullParser的setInput方法解析XML数据
            xmlPullParser.setInput(new StringReader(xmlData));
            //4、调用xmlPullParser的getEventType方法获取当前解析事件
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            //如果当前解析事件并未结束
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //获取当前节点名称
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        if ("app".equals(nodeName)) {
                            LogUtil.d("TestHttpRequest", "id is" + id);
                            LogUtil.d("TestHttpRequest", "name is" + name);
                            LogUtil.d("TestHttpRequest", "version is" + version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
