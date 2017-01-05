package com.example.administrator.text1.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.text1.ui.testOther.TextKeyValueData;
import com.seaway.android.common.widget.circlevp.CirclePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/1/21.
 */
public class CircleAdsAdapter extends CirclePagerAdapter {

    private Context context;
    private List<Drawable> list;

    public CircleAdsAdapter(Context context, List<Drawable> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getRealCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //实例化图片对象
        ImageView imageView = new ImageView(context);
        //设置他的显示类型
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageDrawable(list.get(position % getRealCount()));

        //实例化当前模版对象
//        final AdsItemInfoModel adInfo = list.get(position % getRealCount());
        //初始化图片对象
//        imageView.setTag(adInfo);
//        String uri = list.get(position % getRealCount());
//        //将图片地址转化为图片并设置在imageView里面进行显示
//        Picasso.with(context).load(uri).into(imageView);
        //设置图片点击时间的监听
        imageView.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, TextKeyValueData.class);
//                intent.putExtra(AdWebFragment.ARG_URL, adInfo.getURL());
                context.startActivity(intent);
            }
        });
        container.addView(imageView);
        return imageView;
    }
}

