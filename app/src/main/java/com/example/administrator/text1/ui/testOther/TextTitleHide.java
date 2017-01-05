package com.example.administrator.text1.ui.testOther;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.adapter.Main3Adapter;
import com.example.administrator.text1.utils.view.TextUIDefaultDialogHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：给ListView设置OnTouchListener触屏监听，实现Title显示或是隐藏
 * Created by HM on 2016/2/24.
 */
public class TextTitleHide extends AppCompatActivity implements View.OnClickListener {

    private float mFirstY;
    private float mCurrentY;
    private static final float mTouchSlop = 1;
    private ObjectAnimator mAnimatorTitle;
    private ObjectAnimator mAnimatorContent;


    private RelativeLayout title;
    private TextView txt;
    private FrameLayout bottom;
    private ListView listView;
    private TextView txtHaveRead;
    private TextView txtDeleteAll;
    private List<String> list;
    private Main3Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        title = (RelativeLayout) findViewById(R.id.title);
        txt = (TextView) findViewById(R.id.edit);
        bottom = (FrameLayout) findViewById(R.id.bottom_item);
        listView = (ListView) findViewById(R.id.list);
        txtHaveRead = (TextView) findViewById(R.id.mark_read);
        txtDeleteAll = (TextView) findViewById(R.id.delete_all);


        txt.setOnClickListener(this);
        txtHaveRead.setOnClickListener(this);
        txtDeleteAll.setOnClickListener(this);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {//第一个触摸点
                    //按下
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = event.getY();
                        break;
                    //移动
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = event.getY();
                        //下滑显示
                        if (mCurrentY - mFirstY > mTouchSlop) {
//                            title.setVisibility(View.VISIBLE);
                            showHideTitleBar(true);
                            //上滑隐藏
                        } else if (mFirstY - mCurrentY > mTouchSlop) {
//                            title.setVisibility(View.GONE);
                            showHideTitleBar(false);
                        }
                        break;
                    //抬起
                    case MotionEvent.ACTION_UP:
                        break;

                    default:
                        break;

                }
                return false;
            }
        });

        list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");
        list.add("20");
        adapter = new Main3Adapter(this, list);
        listView.setAdapter(adapter);
        TextAbsract2Activity text1 = new TextAbsract2Activity();
        text1.f1();
        text1.f2();
        text1.f3();
    }

    /**
     * 显示或隐藏Title标题
     *
     * @param tag
     */
    private void showHideTitleBar(boolean tag) {
        if (mAnimatorTitle != null && mAnimatorTitle.isRunning()) {
            mAnimatorTitle.cancel();
        }
        if (mAnimatorContent != null && mAnimatorContent.isRunning()) {
            mAnimatorContent.cancel();
        }
        if (tag) {//显示title(注：“0”表示显示的位置；title.getTranslationY()，相对于自己原来左边(上边)的距离，移动后的数值。)
            mAnimatorTitle = ObjectAnimator.ofFloat(title, "translationY", title.getTranslationY(), 0);
            mAnimatorContent = ObjectAnimator.ofFloat(listView, "translationY", listView.getTranslationY(), 0);

        } else {//隐藏title(注：“-title.getHeight()”表示显示的位置，即表示向上移动title.getHeight()距离)
            mAnimatorTitle = ObjectAnimator.ofFloat(title, "translationY", title.getTranslationY(), -title.getHeight());
            mAnimatorContent = ObjectAnimator.ofFloat(listView, "translationY", listView.getTranslationY(), -title.getHeight());
        }
        mAnimatorTitle.start();
        mAnimatorContent.start();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.edit:
                if (txt.getText().equals("编辑")) {
                    txt.setText("完成");
                    bottom.setVisibility(View.VISIBLE);
                } else if (txt.getText().equals("完成")) {
                    txt.setText("编辑");
                    bottom.setVisibility(View.GONE);
                }
                TextUIDefaultDialogHelper.showDefaultAskAlert(this, "你点我呀！！！", "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextUIDefaultDialogHelper.dialog.dismiss();
                        TextUIDefaultDialogHelper.dialog = null;
                    }
                }, "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextUIDefaultDialogHelper.dialog.dismiss();
                        TextUIDefaultDialogHelper.dialog = null;
                    }
                });
                break;
            case R.id.mark_read:
                bottom.setVisibility(View.GONE);
                txt.setText("编辑");
                break;
            case R.id.delete_all:
                bottom.setVisibility(View.GONE);
                txt.setText("编辑");

                break;
        }
    }
}
