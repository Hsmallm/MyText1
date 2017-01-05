package com.example.administrator.text1.ui.testRecycleView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.DataUtil;

/**
 * Created by hzhm on 2016/9/29.
 * 功能描述：RecycleView实现ChildList的Title悬浮的效果
 * 相关技术: 1、findChildViewUnder(x,y)：表示获取recycleView控件内的子视图控件,从什么坐标开始获得
 * 2、setTranslationY（x）: 设置位移大小，如果A>0：表示向下移动；如果A<0：表示向下移动
 * 3、transInfoView.getTop()：表示距离顶部的距离（getTop：是相对父类控件而言，如果再其上方，则为负值；再其下发，则为正值）
 * 4、stickyInfoView.getContentDescription()、transInfoView.getTag(),获取adapter里面的设置的相应的tag、ContentDescription
 */

public class TestStickyRecycleView extends Activity {

    private TextView title;
    private TextView stickyTitle;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycleview);

        title = (TextView) findViewById(R.id.test_recycleview_title);
        stickyTitle = (TextView) findViewById(R.id.tv_title_header_view);
        recyclerView = (RecyclerView) findViewById(R.id.test_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ContentAdapter(this, DataUtil.getData()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //1、获取recycleView控件内的子视图控件，这个从（0，0）开始。(注：findChildViewUnder(x,y),表示从什么坐标开始获得)
                View stickyInfoView = recyclerView.findChildViewUnder(
                        stickyTitle.getMeasuredWidth() / 2, 5);
                //根据adapter里面给相应的itemView设置的getContentDescription，来设置tvStickyHeaderView文本值
                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    stickyTitle.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                // 2、获取recycleView控件内的子视图控件，这个从tvStickyHeaderView控件以下开始获取
                View transInfoView = recyclerView.findChildViewUnder(
                        stickyTitle.getMeasuredWidth() / 2, stickyTitle.getMeasuredHeight() + 1);

                //当transInfoView不为空且含有title时，即表示滑倒下一个list时
                if (transInfoView != null && transInfoView.getTag() != null) {
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - stickyTitle.getMeasuredHeight();
                    //A、如果transViewStatus为含有title的itemView
                    if (transViewStatus == ContentAdapter.HAS_STICKY_VIEW) {
                        //1、当第二个title还未滑到顶部，则设置tvStickyHeaderView偏移
                        if (transInfoView.getTop() > 0) {
                            //setTranslationY(A):设置位移大小，如果A>0：表示向下移动；如果A<0：表示向下移动
                            stickyTitle.setTranslationY(dealtY);
                        } else {//2、当第二个title滑到甚至超出了顶部,则设置tvStickyHeaderView偏移为0(注:此时stickyInfoView第一个Title再次被找到调用 )
                            stickyTitle.setTranslationY(0);
                        }
                        //B、如果transViewStatus为无title的itemView，则设置tvStickyHeaderView偏移为0
                    } else if (transViewStatus == ContentAdapter.NONE_STICKY_VIEW) {
                        stickyTitle.setTranslationY(0);
                    }
                }
            }
        });
    }
}
