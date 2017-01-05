package com.example.administrator.text1.ui.testAsyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * 功能描述：实现异步加载并显示图片的两种方式、图片缓存
 * 第一种：开启新线程进行异步加载图片
 * 第二种：AsyncTask进行异步加载图片
 *  三（3）：LruCache缓存的使用(LruCache类似与HashMap<Key,Value>一样进行使用)
 *  四（4）：ListView滚动性能优化处理，解决卡顿问题
 *
 * Created by hzhm on 2016/7/8.
 */
public class ImageLoader {

    private ImageView mImageView;
    private String mUrl;
    //创建缓存对象
    private LruCache<String, Bitmap> mCache;
    private ListView mListView;
    private Set<NewsAsyncTask> mTask;

    public ImageLoader(ListView listView) {
        mListView = listView;
        mTask = new HashSet<>();
        //获取最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        //缓存大小的给定
        int cacheSize = maxMemory / 4;
        //3.1实例化cashe对象
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //每次存入缓存时调用
                return value.getByteCount();
            }
        };
    }

    /**
     * 添加到缓存
     *
     * @param key
     * @param value
     */
    public void addBitmapToCache(String key, Bitmap value) {
        if (getBitmapfromCache(key) == null) {
            mCache.put(key, value);
        }
    }

    /**
     * 从缓存中获取
     *
     * @param url
     * @return
     */
    public Bitmap getBitmapfromCache(String url) {
        return mCache.get(url);
    }

    /**
     * handleMessage里面处理异步线程发送过来的msg.obj对象，从而操作UI线程
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ///(注：给ImageView设置tag标签，解决listView缓存机制的问题)
            if (mImageView.getTag().equals(mUrl)) {
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    /**
     * 第一种：开启新线程获取图片资源
     *
     * @param imageView
     * @param url
     */
    public void showImageByThread(ImageView imageView, final String url) {
        mImageView = imageView;
        mUrl = url;
        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapfromURL(url);
                //非主线程不能操作UI主线程界面，所以发送message对象给handle进行处理
                Message message = Message.obtain();
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        }.start();
    }

    /**
     * 通过URL图片网站获取图片资源
     *
     * @param urlString
     * @return
     */
    public Bitmap getBitmapfromURL(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 第二种：AsyncTask进行异步加载图片
     *
     * @param imageView
     * @param url
     */
    public void showImageByAsyncTask(ImageView imageView, final String url) {
        //3.3从缓存中取出对应的图片
        Bitmap bitmap = getBitmapfromCache(url);
        if (bitmap == null) {
            new NewsAsyncTask(url).execute(url);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }


    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {
        //        ImageView mImageView;
        String mUrl;

        public NewsAsyncTask(String url) {
//            mImageView = imageView;
            mUrl = url;
        }

        /**
         * 执行耗时操作，及加载图片
         *
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            //3.2从网络获取图片
            Bitmap bitmap = getBitmapfromURL(params[0]);
            if (bitmap != null) {
                //将不再缓存中的图片添加入缓存
                addBitmapToCache(params[0], bitmap);
            }
            return bitmap;
        }

        /**
         * 加载成功后执行.....(注：AsyncTask这里面的方法可直接操作UI线程)
         *
         * @param bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            //当AsyncTask执行完毕后，移除相应的AsyncTask
            mTask.remove(this);
        }
    }

    /**
     * 4.1、当停止滚动时，用于加载显示从start到end所有项
     * @param start
     * @param end
     */
    public void loadImage(int start, int end) {
        for (int i = start; i < end; i++) {
            String url = NewsAdapter.URLS[i];
            //3.3从缓存中取出对应的图片
            Bitmap bitmap = getBitmapfromCache(url);
            if (bitmap == null) {
                NewsAsyncTask task = new NewsAsyncTask(url);
                task.execute(url);
                mTask.add(task);
            } else {
                //通过Tag标签找到相应ListView中的ImageView
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * 4.2、当滚动进行时，停止当前正在运行的所有任务
     */
    public void cancleAllTask() {
        if (mTask != null) {
            for (NewsAsyncTask task : mTask) {
                task.cancel(false);
            }
        }
    }
}
