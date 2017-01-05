package com.example.administrator.text1.ui.testPicasso;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.example.administrator.text1.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * 功能描述：Android 图片加载框架Picasso
 * 详情描述：Picasso时Squera公司开发的一套Android图形的缓存库
 * Picasso集成了图片的异步加载，并集成了图片加载显示的一系列问题：
 * 1、自带内存和硬盘的二级缓存机制，完美解决OOM内存溢出的问题
 * 2、并使用复杂的图片转换技术降低图片的内存使用率
 * 3、Adapter中取消了不在视图范围内的ImageView的资源加载,因为可能会产生图片错位;
 * (使用ListView，GridView的时候，自动检测Adapter的重用（re-use），取消下载，使用缓存。)
 * Created by hzhm on 2016/8/2.
 */
public class TestPicasso extends Activity {
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";
    private ImageView  picassoImg;
    private static final String URL = "http://www.jycoder.com/json/Image/1.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_picasso);
        picassoImg = (ImageView) findViewById(R.id.picasso_img);
        getUriImg();
    }

    /**
     * 1、网络读取图片
     */
    public void getInternetImg() {
        Picasso
                .with(this)//实例化
                .load(URL)//加载url
                .placeholder(R.drawable.trphoto)//设置默认图片
                .error(R.drawable.trphoto)//设置加载失败指定图片
                .into(picassoImg);//设置显示容器

    }

    /**
     * 2、从Android Resources 中读取图片
     */
    public void getDarwableImg() {
        Picasso
                .with(this)
                .load(R.drawable.trcoin)
                .placeholder(R.drawable.trphoto)
                .error(R.drawable.trphoto)
                .into(picassoImg);
    }

    /**
     * 3、从本地File文件夹中 Pictures目录读取图片
     */
    public void getFileImg() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"地球.png");
        Picasso
                .with(this)
                .load(file)
                .placeholder(R.drawable.trphoto)
                .error(R.drawable.trphoto)
                .into(picassoImg);
    }

    /**
     * 4、从URI地址中加载
     */
    public void getUriImg() {
        Uri uri = resourceIdToUri(this,R.drawable.trcheck);
        Picasso
                .with(this)
                .load(uri)
                .resize(10,10)//调整大小
                .placeholder(R.drawable.trphoto)
                .error(R.drawable.trphoto)
                .into(picassoImg);
    }

    /**
     * 指定本地资源图片的uri路径
     *
     * @param context
     * @param resourceId
     * @return
     */
    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    private class CropSquareTransformation extends Transformation {

        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        public String key() {
            return "square()";
        }
    }
}
