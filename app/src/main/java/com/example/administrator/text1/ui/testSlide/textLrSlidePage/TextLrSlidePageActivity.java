package com.example.administrator.text1.ui.testSlide.textLrSlidePage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * 功能描述：自定义（custom）ViewGroup，实现多视图的切换功能
 * 1、点击相关按钮实现页面的切换
 * 2、左右滑动切换相应的页面
 * Created by HM on 2016/1/13.
 *
 */

public class TextLrSlidePageActivity extends Activity implements View.OnClickListener {

    private static final String argUrl = "http://www.360taihe.com/";
    private static final String title = "泰和网";

    private Button button;

    private TextViewGroup textViewGroup;
    private Button prveBtn;
    private Button reBackBtn;
    private Button nextBtn;
    private Button moveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_textviewgroup);

        textViewGroup = (TextViewGroup) findViewById(R.id.viewgroup);
        prveBtn = (Button) findViewById(R.id.prev);
        reBackBtn = (Button) findViewById(R.id.reback);
        nextBtn = (Button) findViewById(R.id.next);
        moveBtn = (Button) findViewById(R.id.move);

        prveBtn.setOnClickListener(this);
        reBackBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        moveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        UmengShareConfig.shareUrl(R.drawable.share_logo, this, argUrl, "注册泰和网，红包马上有！", title);
        switch (v.getId()){
            case R.id.prev:
                //切换到第三个视图
                textViewGroup.scrollTo(-768, 0);
                Log.e("aaa",textViewGroup.getScaleX()+"");
                break;
            case R.id.reback:
                //切换到第二个视图
                textViewGroup.scrollTo(0,0);
                break;
            case R.id.next:
                //切换到第一个视图
                textViewGroup.scrollTo(768, 0);
                break;
            case R.id.move:
                textViewGroup.scrollBy(20,0);
                break;
        }
    }
}
