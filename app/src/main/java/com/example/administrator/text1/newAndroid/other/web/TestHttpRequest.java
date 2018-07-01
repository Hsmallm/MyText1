package com.example.administrator.text1.newAndroid.other.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.newAndroid.other.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.http.GET;

/**
 * @author HuangMing on 2017/12/29.
 *         功能描述：使用Http协议访问网络
 *         1、Android上发送Http请求的两种方式？？？
 *         a、HttpURLConnection这种也是官方推荐版
 *         b、OkHttp第三方开源库
 *         2、网络上数据传输的两种格式？？？
 *         a、XML格式，解析XML数据格式的两种方式(注：XML格式的数据语意比较清晰，但是传输比较耗流量)
 *            a.1、Pull
 *            a.2、SAX
 *         b、JSON格式，解析JSON数据格式的两种方式(注：JSON格式的数据语意不是很清晰，但是传输比较节省流量)
 *            b.1、JsonObject
 *            b.2、GSON
 */

public class TestHttpRequest extends AppCompatActivity {

    private TextView textView;

    private HttpURLConnection connection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_http);

        Button btn = (Button) findViewById(R.id.btn);
        textView = (TextView) findViewById(R.id.text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttp();
            }
        });
    }

    /**
     * 使用HttpURLConnection发送http请求
     */
    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader reader = null;
                try {
                    //实例化URL对象
                    URL url = new URL("https://www.baidu.com");
                    //实例化HttpURLConnection，即发送http请求..
                    connection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    connection.setRequestMethod("GET");
                    //设置连接超时时间
                    connection.setConnectTimeout(8000);
                    //设置读取超时时间
                    connection.setReadTimeout(8000);
                    //获取服务端返回的输入流数据
                    InputStream in = connection.getInputStream();
                    //通过BufferedReader读取输入流数据
                    reader = new BufferedReader(new InputStreamReader(in));
                    //通过BufferedReader解析后的数据
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 使用OkHttp发送http请求(OkHttp：第三方网络通信库，由Square公司开发)
     */
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
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void showResponse(final String txt) {
        //runOnUiThread：即在主线程中处理相关UI更新操作
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(txt);
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

    /**
     * SAX解析Xml数据格式
     *
     * @param xmlData
     */
    private void parseXMLWithSax(String xmlData) {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
            MyHandler myHandler = new MyHandler();
            //将myHandler的实例设置到xmlReader
            xmlReader.setContentHandler(myHandler);
            //开始解析...
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * JSONObject解析JSON数据
     *
     * @param jsonData
     */
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            //JSONArray用于解析一段json数组
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                //JSONObject用于解析一段json数据
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                LogUtil.d("id", "id is" + id);
                LogUtil.d("name", "id is" + name);
                LogUtil.d("Version", "id is" + version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * GSON解析JSON数据(注：可以将json数据自动映射成一个对象，若是一段Json数组的话，需要借助TypeToken将期待解析成的数据传入到formJson中)
     *
     * @param jsonData
     */
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<AppData> appDataList = gson.fromJson(jsonData, new TypeToken<List<AppData>>() {
        }.getType());
        for (AppData appData : appDataList) {
            LogUtil.d("id", "id is" + appData.getId());
            LogUtil.d("name", "name is" + appData.getName());
            LogUtil.d("version", "version is" + appData.getVersion());
        }
    }
}