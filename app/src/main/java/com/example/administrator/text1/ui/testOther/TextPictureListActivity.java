package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 功能描述：创建一个图片列表TextPictureListActivity类，实现图片列表和动画显示功能
 * 技术要点：1、recyclerView的使用；2、异步图片加载；3、图片动画（动态）的显示
 * Created by HM on 2016/4/14.
 */
public class TextPictureListActivity extends Activity {

    public static final int MSG_INIT = 0;
    public static final String MYURL = "http://101.71.241.100:8686/hzcms//userfiles/1/images/cms/link/2016/03/2.png?t=1458720594000";
    public static final String MYURL2 = "http://101.71.241.100:8686/hzcms//userfiles/1/images/cms/link/2016/03/1.png?t=1464948252000";

    private RecyclerView recyclerView;
    private List<String> list;
    /**
     * 创建一个匿名内部内实例化RecyclerView.Adapter
     */
    private RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        //HashSet类，是存在于java.util包中的类。同时也被称为集合，该容器中只能存储不重复的对象，
        HashSet<Integer> hashSet = new HashSet();

        //onCreateViewHolder里面实例化view视图及相关组建,并返回ItemHolder
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(TextPictureListActivity.this).inflate(R.layout.adapter_home_item, parent, false);
            //
            Animation animation = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            animation.setDuration(300);
            //View中的setTag(Onbect)表示给View添加一个格外的数据，以后可以用getTag()将这个数据取出来。
            view.setTag(3<<24,animation);
            return new ItemHolder(view);
        }

        //onBindViewHolder里面进行数据的绑定
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemHolder holder1 = (ItemHolder) holder;
            holder1.bind(list.get(position));
            if(position > 2 && hashSet.add(position)){
                //通过getTag()将这个数据从View中取出来
                Animation animation = (Animation) holder1.itemView.getTag(3<<24);
                animation.cancel();
                ///ScaleAnimation 缩放动画效果
                //（注：0.0f：表示动画开始是的放大大小;1.4f:表示动画结束时的放大大小Animation.RELATIVE_TO_SEL:表示他的动画模式
                // 一般这两个参数配合使用才能达到围绕这图像中心点旋转的效果）
                holder1.itemView.findViewById(R.id.view).startAnimation(animation);
            }
        }

        //返回列表数
        @Override
        public int getItemCount() {
            return list == null? 0:list.size();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_picturelist);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        list.add(MYURL);
        list.add(MYURL2);
        list.add(MYURL);
        list.add(MYURL2);
        list.add(MYURL);
        list.add(MYURL2);
        list.add(MYURL);
        list.add(MYURL2);
        list.add(MYURL);
        list.add(MYURL2);
        list.add(MYURL);
        list.add(MYURL2);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /**
     * 创建一个RecyclerView.ViewHolder普通内部类
     */
    class ItemHolder extends RecyclerView.ViewHolder {

        //给itemView设置点击事件监听
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.showToast(TextPictureListActivity.this,"aa");
            }
        };

        ImageView imageView;
        View vFinish;

        //创建一个构造方法实例这个类，并初始化相关组建
        public ItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(onClickListener);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            vFinish = itemView.findViewById(R.id.over_cover);
        }

        public void bind(String url){
            itemView.setTag(itemView);
            vFinish.setVisibility(View.GONE);
            imageView.setAlpha(1f);
            //开启新线程，启动网络获取图片
            new newThread(url).start();
        }


        /**
         * 创建一个Handle匿名内部类，接收子线程中传过来的msg，从而UI界面的更新操作
         */
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case MSG_INIT:
                        Bitmap bitmap = (Bitmap) msg.obj;
                        imageView.setImageBitmap(bitmap);
                        break;
                }
            }
        };

        /**
         * 创建一个newThread普通内部类，即一个新线程
         */
        class newThread extends Thread{
            private String url;

            //创建一个构造方法实例化这个类
            public newThread(String url){
                this.url =url;
            }

            //run方法里面执行耗时的网络请求操作
            @Override
            public void run() {
                super.run();
                URL myFileUrl;
                Bitmap bitmap = null;

                try {
                    myFileUrl = new URL(url);
                    //获得网络连接对象
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    //设置连接超时时间
                    conn.setConnectTimeout(6000);
                    //连接设置获得数据流
                    conn.setDoInput(true);
                    //不使用缓存
                    conn.setUseCaches(false);
                    //获得数据流
                    InputStream stream = conn.getInputStream();
                    //然后再将数据流强转为Bitmap类型
                    bitmap = BitmapFactory.decodeStream(stream);
                    //关闭数据流
                    stream.close();
                    //通过handler对象将子线程中的获取对象传回主线程
                    handler.obtainMessage(MSG_INIT,bitmap).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
