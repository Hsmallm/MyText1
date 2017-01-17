package com.example.administrator.text1.ui.testScanCode.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by hzhm on 2017/1/16.
 * 功能描述：测试SurfaceView类的相关使用...
 * 整个过程：继承SurfaceView并实现SurfaceHolder.Callback接口 ----> SurfaceView.getHolder()获得SurfaceHolder对象
 *          ---->SurfaceHolder.addCallback(callback)添加回调函数---->SurfaceHolder.lockCanvas()获得Canvas对象并锁定画布
 *          ----> Canvas绘画 ---->SurfaceHolder.unlockCanvasAndPost(Canvas canvas)结束锁定画图，并提交改变，将图形显示。
 */

public class TestSurfaceView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    class MyView extends SurfaceView implements SurfaceHolder.Callback {

        private SurfaceHolder holder;
        private MyThread myThread;

        public MyView(Context context) {
            super(context);
            holder = this.getHolder();
            holder.addCallback(this);
            myThread = new MyThread(holder);
        }

        //---在创建时激发，一般在这里调用画图的线程。
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            myThread.isRun = true;
            myThread.start();
        }

        //---在surface的大小发生改变时激发
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        //---销毁时激发，一般在这里将画图的线程停止、释放。
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            myThread.isRun = false;
        }
    }

    class MyThread extends Thread {

        private SurfaceHolder holder;
        private boolean isRun;

        public MyThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
        }

        @Override
        public void run() {
            super.run();
            int count = 0;
            while (isRun) {
                Canvas c = null;
                try {
                    synchronized (holder) {
                        c = holder.lockCanvas();//锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
                        c.drawColor(Color.BLACK);//设置画布背景颜色
                        Paint p = new Paint(); //创建画笔
                        p.setColor(Color.WHITE);
                        Rect r = new Rect(100, 50, 300, 250);
                        c.drawRect(r, p);
                        c.drawText("这是第" + (count++) + "秒", 100, 310, p);
                        Thread.sleep(1000);//睡眠时间为1秒
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);//结束锁定画图，并提交改变。
                    }
                }
            }
        }
    }
}
