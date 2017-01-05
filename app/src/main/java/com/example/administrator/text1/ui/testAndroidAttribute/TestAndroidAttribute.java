package com.example.administrator.text1.ui.testAndroidAttribute;

import android.app.Activity;
import android.os.Bundle;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/11/2.
 * 功能描述：ImageView的ScaleType属性
 * 1、ScaleType属性的含义？
 *    .决定了图片在View上显示时的样子，如进行何种比例的缩放，及显示图片的整体还是部分，等等。
 * 2、ScaleType属性的多种属性类型的介绍与使用？
 *    .SetScaleType(ImageView.ScaleType.CENTER):按图片的原来size居中显示，当图片长/宽超过View的长/宽，则截取图片的居中部分显示
 *    .SetScaleType(ImageView.ScaleType.CENTER_CROP):按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)
 *    .SetScaleType(ImageView.ScaleType.CENTER_INSIDE):将图片的内容完整居中显示，通过按比例缩小或原来的size使得图片长/宽等于或小于View的长/宽
 *    .SetScaleType(ImageView.ScaleType.FIT_CENTER):把图片按比例扩大/缩小到View的宽度，居中显示;
                                                    FIT_START、FIT_END在图片缩放效果上与FIT_CENTER一样，只是显示的位置不同，FIT_START是置于顶部，FIT_CENTER居中，FIT_END置于底部。
 *    .SetScaleType(ImageView.ScaleType.FIT_XY):  不按比例缩放图片，目标是把图片塞满整个View。
 */

public class TestAndroidAttribute extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_android_attribute);
    }
}
