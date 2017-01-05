package com.example.administrator.text1.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.administrator.text1.TextAppliction;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * 功能描述：图片加载显示工具类（主要是Picasso这个类的应用)
 * Created by Administrator on 2015/12/4.
 */
public class ImageLoadUtil {

    /**
     * @param imageView
     * @param url
     * @param holderRes
     * @param errorRes
     * @description 加载网络图片, 如：广告轮播页，显示头像的对话框等
     */
    public static void loadNormalImage(ImageView imageView, String url, int holderRes, int errorRes) {
        String str = TextUtils.isEmpty(url) ? "http://????" : url;
        Picasso.with(TextAppliction.getInstance())
                .load(str)
                .placeholder(holderRes)
                .error(errorRes)
                .fit()
                .into(imageView);
    }

    /**
     * @param imageView
     * @param url
     * @description 加载网络图片
     */
    public static void loadGridImg(final ImageView imageView,String url) {
        String str = TextUtils.isEmpty(url) ? "http://????" : url;
        Picasso.with(TextAppliction.getInstance())
                .load(str).noPlaceholder().error((Drawable) imageView.getTag()).into(imageView);
    }

    /**
     * @param imageView
     * @param res
     * @param holderRes
     * @param errorRes
     * @description 加载本地图像，如：引导页
     */
    public static void loadLocalNormalImage(ImageView imageView, int res, int holderRes, int errorRes) {
        Picasso.with(TextAppliction.getInstance())
                .load(res)
                .placeholder(holderRes)
                .error(errorRes)
                .fit()
                .into(imageView);
    }

    /**
     * @param imageView
     * @param url
     * @description 启动页图片
     */
    public static void loadSplashImage(ImageView imageView, String url) {
        String str = TextUtils.isEmpty(url) ? "http://????" : url;
        Picasso.with(TextAppliction.getInstance())
                .load(str)
                .into(imageView);
    }

    /**
     * @param imageView
     * @param url
     * @param holderRes
     * @param errorRes
     * @description 加载头像
     */
    public static void loadAvatarImage(ImageView imageView, String url, int holderRes, int errorRes) {
        String str = TextUtils.isEmpty(url) ? "http://????" : url;
        Picasso.with(TextAppliction.getInstance())
                .load(str)
                .resize(300, 300)
                .placeholder(holderRes)
                .error(errorRes)
                .centerCrop()
                .into(imageView);
    }

    /**
     * @param imageView
     * @param file
     * @param holderRes
     * @param errorRes
     * @description 加载头像
     */
    public static void loadFileImage(Context context, ImageView imageView, File file, int holderRes, int errorRes) {
        Picasso.with(context)
                .load(file)
                .resize(300, 300)
                .placeholder(holderRes)
                .error(errorRes)
                .centerCrop()
                .into(imageView);
    }
}
