package com.example.administrator.text1.ui.test3D;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.text1.R;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

/**
 * 功能描述：实现图片滚动的3D画廊效果
 * Created by hzhm on 2016/7/12.
 */
public class Test3DGallery extends Activity {

    private int[] images = {R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5};
    private FancyCoverFlow mFancyCoverFlow;
    private FancyCoverFlowAdapter mAdapter = new FancyCoverFlowAdapter() {
        @Override
        public View getCoverFlowItem(int position, View reusableView, ViewGroup parent) {
            ImageView iv = null;
            //如果有可以重用的ImageView对象，直接返回即可
            if(reusableView != null){
                iv = (ImageView) reusableView;
            //如果没有可以重用的ImageView则是创建一个
            }else {
                iv = new ImageView(parent.getContext());
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                iv.setLayoutParams(new FancyCoverFlow.LayoutParams(300,500));
            }
            //设置当前爱你显示资源图片
            iv.setImageResource((Integer) getItem(position));
            return iv;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_3d_gallery);

        mFancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
        mFancyCoverFlow.setAdapter(mAdapter);
        //设置未选择图像的透明度
        mFancyCoverFlow.setUnselectedAlpha(0.2f);
        //设置未选中图像的色彩饱和度
        mFancyCoverFlow.setUnselectedSaturation(0f);
        //设置未选择图像的缩放比例
        mFancyCoverFlow.setUnselectedScale(0.5f);
        //设置未选择图像之间的距离
        mFancyCoverFlow.setSpacing(10);
        //设置未选择图像的最大旋转角度
        mFancyCoverFlow.setMaxRotation(0);

        //设置倒影是否显示
        mFancyCoverFlow.setReflectionEnabled(true);
        //设置未被选中图片的下沉
        mFancyCoverFlow.setScaleDownGravity(0.8f);
        mFancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
    }
}
