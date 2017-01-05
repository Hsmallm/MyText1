package com.example.administrator.text1.ui.testBitmapCompress;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.text1.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by hzhm on 2016/11/3.
 * <p>
 * 功能描述：测试相关Bitmap常用知识点及其技巧
 * 相关技术点：1、itmap.setConfig设置Bitmap的Bitmap.Config属性
 * 2、获取图片内存大小的两种方式：bitmap.getByteCount、bitmap.getWidth() * bitmap.getHeight() * 4
 * 3、测试Bitmap.compress()图片压缩方法
 * 4、文件的三种存储形式：文件、流、bitmap
 */

public class TestBitmapCompress extends Activity {

    private ImageView imageView;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_android_attribute);

        Resources resources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.hm);

        //------1、itmap.setConfig设置Bitmap的Bitmap.Config属性
        //注A：此方法不是所以的位图都可以适用（异常：java.lang.IllegalStateException）
        //注B：从Android4.0开始，该选项无效。即使设置为该值，系统任然会采用 ARGB_8888来构造图片
//        bitmap.copy(Bitmap.Config.ALPHA_8,true);
//        bitmap.setConfig(Bitmap.Config.ALPHA_8);

        //解决方案：先将Bitmap转化为字节流，在通过BitmapFactory将字节流转化为Bitmap并设置Bitmap.Config
        ByteArrayOutputStream dataByte = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, dataByte);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        opts.inPreferredConfig = Bitmap.Config.ALPHA_8;
        bitmap = BitmapFactory.decodeByteArray(dataByte.toByteArray(), 0, dataByte.size(), opts);
        Bitmap.Config alpha8 = bitmap.getConfig();


        Log.e("Bitmap.Config：", alpha8 + "");

        //------2、获取图片内存大小的三种方式：bitmap.getByteCount、bitmap.getRowbytes*bitmap.getHeight、bitmap.getWidth() * bitmap.getHeight() * 4
        //注A：图片内存大小 = 图片的长 * 图片的宽 * 单位像素所占有的字节数
        //注B：单位像素所占有的字节数，又是由Bitmap.Config有关,Bitmap.Config是Bitmap里面的枚举类型，总共由四种类型,默认类型为ARGB_8888，且不可更改，4个字节。
        //注C：bitmap.getByteCount,这个方法是4版本加上的，2版本是没有的;第二种、第三种都是兼容低版本的
        Log.e("bitmap第一种获取图片内存大小方式：", bitmap.getByteCount() / 1024 + "");
        Log.e("bitmap第二种获取图片内存大小方式：", bitmap.getRowBytes() * bitmap.getHeight() / 1024 + "");
        Log.e("bitmap第三种获取图片内存大小方式：", bitmap.getWidth() * bitmap.getHeight() * 4 / 1024 + "");


        //------3、测试Bitmap.compress()图片压缩方法、4、文件的三种存储形式：文件、流、bitmap
        //总结：1、bitmap.compress()：这个方法压缩只是图片的文件大小，即为质量大小；不会进行图片分辨率大小，即内存大小的压缩
        //      2、图片以文件、流形式的时候都是以一种压缩的存储形式来存储的；
        //         bitmap对象，api中介绍说是把每个像素存储起来，我觉得可以理解成散开的豆子，自然占的空间大；可能也跟存储结构有关系的
        int size = bitmap.getWidth() * bitmap.getHeight() * 4 / 1024;
        Log.e("压缩前的图片内存大小：", size + "");
        ByteArrayOutputStream dataByte2 = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, dataByte2);
        int compressSize = bitmap.getWidth() * bitmap.getHeight() * 4 / 1024;
        Log.e("压缩后的图片内存大小：", compressSize + "");
        Log.e("压缩后图片的文件大小", dataByte2.toByteArray().length / 1024 + "");
    }
}
