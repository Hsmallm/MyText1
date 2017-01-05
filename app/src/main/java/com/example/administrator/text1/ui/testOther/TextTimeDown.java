package com.example.administrator.text1.ui.testOther;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.text1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述：---- handler.postDelayed(new runnable)实现倒计时功能.....
 * (注：new Runnable并不一定是新开了一个线程)
 * Created by HM on 2016/1/11.
 */
public class TextTimeDown extends AppCompatActivity {

    private SimpleDateFormat dateFormat;//格式化时间对象
    private Long downtoTime;//时间对象
    private Long serverTime;
    private TextView txtDownToTime;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //格式化时间对象
        dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        //获取当前时间
        downtoTime = System.currentTimeMillis();
        Log.e("downtoTime", downtoTime + "");
        try {
            //将String字符串类型转化为Long长整型
            serverTime = stringToLong("04-02 15:00:00","MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("serverTime", serverTime + "");

        downtoTime = downtoTime - serverTime;
        Log.e("downtoTime", downtoTime + "");
        txtDownToTime = (TextView) findViewById(R.id.downtotime);

        //new一个Handle对象
        handler = new Handler();

        //new一个Runnable对象,并实现其run方法
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                downtoTime = downtoTime - 1000;
                Log.e("downtoTime", downtoTime + "");
                txtDownToTime.setText(dateFormat.format(downtoTime));
                Log.e("txtDownToTime", dateFormat.format(downtoTime) + "");
                //第二次、三次....(每隔一秒执行一次)
                handler.postDelayed(this, 1000);
                //如果满足条件结束当前handler
                if (dateFormat.format(downtoTime).equals("0")) {
                    txtDownToTime.setText("OK!");
                    //结束当前handler
                    handler.removeCallbacks(this);
                }
            }
        };

        //第一次进入主程序时所需执行的...(注：该方法并非为开启一个新的线程，在Runnable---run里面完成耗时操作，完成之后发送消息给post，完成UI更新；)
        handler.postDelayed(runnable, 100);
    }


    ///格式化时间方法
    public static String getDuration(long durationSeconds) {
        long hours = durationSeconds / (60 * 60);
        long leftSeconds = durationSeconds % (60 * 60);
        long minutes = leftSeconds / 60;
        long seconds = leftSeconds % 60;

        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(addZeroPrefix(hours));
        sBuffer.append(":");
        sBuffer.append(addZeroPrefix(minutes));
        sBuffer.append(":");
        sBuffer.append(addZeroPrefix(seconds));

        return sBuffer.toString();
    }

    public static String addZeroPrefix(long number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return "" + number;
        }

    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Long stringToLong(String strTime, String formatType) throws ParseException {
        Date date = stringToDate(strTime, formatType); // 第一步：String类型转成date类型
        if (date == null) {
            return null;
        } else {
            long currentTime = dateToLong(date); // 第二步：date类型转成long类型
            return currentTime;
        }
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

}
