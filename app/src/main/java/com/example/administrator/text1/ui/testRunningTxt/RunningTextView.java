package com.example.administrator.text1.ui.testRunningTxt;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.text1.utils.ExHandler;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hzhm on 2016/11/23.
 * 功能描述：数字滚动播放器
 * A、实现原理：使用ExecutorService开启一个新线程；
 * 一：在新线程里面：计算每帧滚动的数字，并handle.sendMessage将该数字添加到主线程的消息队列
 * 二：在handleMessage里面：先是进行UI的更新、数字的加减；再是与最终显示的字段进行比较，如果没能满足条件,继续handler.sendMessageDelayed(msg2, 40)
 * 将消息添加到主线程消息队列，等待UI的线程继续执行；如果满足条件，则就显示最终的文本字段，不再发送消息到主线程消息队列。
 *
 * B、handle简要：

    1、Handler这个类就是管理某个线程(也可能是进程)的消息队列

    2、Handler也没那么简单，内部必须的和Looper进行关联，即线程相关联（默认与主线程相关联）

    3、Handler可以分发Message对象和Runnable对象到相应线程消息队列中，等待执行（即相应的线程中）
        例如：a、handler.sendMessageDelayed(msg2, 40);//将Message对象延时40毫秒，发送到主线程中
              b、handler.sendMessage(msg2);//将Message对象发送到主线程中
              c、分发Message对象和Runnable对象的区别：分发的Message对象到相应的线程队列中，只能配合相应的线程进行操作(并非一段可执行的程序)；
                                                      分发的Runnable对象到相应的线程队列中，是一段可执行的程序(一段可执行的程序)

    4、它与子线程可以通过Message对象来传递数据
 */

public class RunningTextView extends TextView {

    public double content;// 最后显示的数字
    private int frames = 40;// 总共跳跃的帧数,默认25跳
    private double nowNumber = 0.00;// 显示的时间
    private ExecutorService thread_pool;
    private Handler handler;
    private DecimalFormat formater;// 格式化时间，保留两位小数

    // 设置开始显示的数字，默认不设置数字从0.00开始跳动，设置了则数字从大向小跳
    private double startNmb = 0.00;
    private boolean isSub = true;
    private long firstPauseTime;
    private long firstTime;
    private long secoedTime;

    public RunningTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RunningTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RunningTextView(Context context) {
        super(context);
        init();
    }

    /**
     * 设置开始显示数目
     *
     * @param startNmb
     */
    public void setStartNmb(double startNmb) {
        this.startNmb = startNmb;
    }

    /**
     * 设置增加还是减少
     *
     * @param isSub
     */
    public void setIsSub(boolean isSub) {
        this.isSub = isSub;
    }

    public int getFrames() {
        return frames;
    }

    // 设置帧数
    public void setFrames(int frames) {
        this.frames = frames;
    }

    /**
     * 设置数字格式，具体查DecimalFormat类的api
     *
     * @param pattern
     */
    public void setFormat(String pattern) {
        formater = new DecimalFormat(pattern);
    }

    // 初始化
    private void init() {
        firstPauseTime = System.currentTimeMillis();
        thread_pool = Executors.newFixedThreadPool(1);// 2个线程的线程池
        if (formater == null)
            formater = new DecimalFormat("#,##0.00");// 最多两位小数，而且不够两位整数用0占位。可以通过setFormat再次设置
        formater.setRoundingMode(RoundingMode.FLOOR);//设置数字的取舍模式
        handler = new ExHandler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                RunningTextView.this.setText(formater.format(nowNumber));// 更新显示的数字
                if (!isSub) {
                    nowNumber -= Double.parseDouble(msg.obj.toString());// 跳跃arg1那么多的数字间隔
                } else {
                    nowNumber += Double.parseDouble(msg.obj.toString());// 跳跃arg1那么多的数字间隔
                }
                if (isSub) {
                    if (nowNumber < content) {
                        Message msg2 = handler.obtainMessage();
                        msg2.obj = msg.obj;
                        //-----------------------此方法就是实现数字的轮播的重要方法-------------------------
                        handler.sendMessageDelayed(msg2, 40);// 继续发送通知改变UI(即：handleMessage将会不断的接收处理消息)
//                        handler.sendMessage(msg2);
                        secoedTime = System.currentTimeMillis();
                        long time = secoedTime - firstTime;
                        //打印输出每次增加数额的间隔时间
                        Log.e("firstTime", time + "");
                    } else {
                        RunningTextView.this.setText(formater.format(content).toString());// 最后显示的数字，动画停止
                        long lastPauseTime = System.currentTimeMillis();
                        long time2 = lastPauseTime - firstPauseTime;
                        //打印输出最终整个文字滚动的耗时时间
                        Log.e("lastPauseTime", time2 + "");
                    }
                } else {
                    if (nowNumber > content) {
                        Message msg2 = handler.obtainMessage();
                        msg2.obj = msg.obj;
                        handler.sendMessageDelayed(msg2, 40);// 继续发送通知改变UI(即：handleMessage将会不断的接收处理消息)
//                        handler.sendMessage(msg2);
                        secoedTime = System.currentTimeMillis();
                        long time = secoedTime - firstTime;
                        //打印输出每次增加数额的间隔时间
                        Log.e("firstTime", time + "");
                    } else {
                        RunningTextView.this.setText(formater.format(content).toString());// 最后显示的数字，动画停止
                        long lastPauseTime = System.currentTimeMillis();
                        long time2 = lastPauseTime - firstPauseTime;
                        //打印输出最终整个文字滚动的耗时时间
                        Log.e("lastPauseTime", time2 + "");
                    }
                }
            }
        };
    }

    /**
     * 播放数字动画的方法
     *
     * @param moneyNumber
     */
    public void playNumber(double moneyNumber) {
//		if (moneyNumber == 0) {
//			RunningTextView.this.setText("0.00");
//			return;
//		}
        if (!isSub) {
            nowNumber = startNmb;
        } else {
            nowNumber = 0.00;// 默认都是从0开始动画
        }
        content = moneyNumber;// 设置最后要显示的数字

        startExecutorService();
    }

    /**
     * -----------------------此方法就是实现数字的轮播的重要方法（但是此线程并未进行耗时操作，主要就是配合进行数字轮播的实现）-------------------------
     * 开启新线程方式一：new Thread(new Runnable()）
     */
    private void startNewThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                firstTime = System.currentTimeMillis();
                double temp = content / frames;
                if (!isSub) {
                    temp = Math.abs((startNmb - content) / frames);
                }

                //延时操作
//                try {
//                    Thread.sleep(40);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Message msg = handler.obtainMessage();
                msg.obj = temp;// 如果每帧的间隔比1小，就设置为1
                handler.sendMessage(msg);// 发送通知改变UI
            }
        }).start();
    }

    /**
     * -----------------------此方法就是实现数字的轮播的重要方法（但是此线程并未进行耗时操作，主要就是配合进行数字轮播的实现）-------------------------
     * 开启新线程方式二：thread_pool.execute(new Runnable()）
     */
    private void startExecutorService() {
        thread_pool.execute(new Runnable() {
            @Override
            public void run() {

                firstTime = System.currentTimeMillis();
                double temp = content / frames;
                if (!isSub) {
                    temp = Math.abs((startNmb - content) / frames);
                }

                //延时操作
//                try {
//                    Thread.sleep(40);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Message msg = handler.obtainMessage();
                msg.obj = temp;// 如果每帧的间隔比1小，就设置为1
                handler.sendMessage(msg);// 发送通知改变UI
            }
        });
    }
}
