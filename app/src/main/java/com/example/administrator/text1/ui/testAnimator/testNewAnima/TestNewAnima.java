package com.example.administrator.text1.ui.testAnimator.testNewAnima;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2017/2/21.
 * <p>
 * 功能描述：Acticity转场动画
 * 简介：Android5.0之后又推出的新的转场动画，Activity的出入场动画总体上来说可以分为两种：
 * A、一种就是分解、滑动进入、淡入淡出(非共享元素型)；B、另外一种就是共享元素动画(共享元素型)
 * <p>
 * Acticity旧的转场动画回顾：Activity旧的的转场动画是通过overridePendingTransition(int enterAnim, int exitAnim)实现的,
 * 这个方法在startActivity(Intent) or finish()之后被调用，指定接下来的这个转场动画。
 * 方法的第一个参数：enterAnim，是新的Activity的进入动画的resource ID；
 * 第二个参数exitAnim，是旧的Activity(当前的Activity)离开动画的resource ID。
 * 他们的作用对象是两个Activity,如果上面两个参数没有动画要设置，则用0作为参数。
 * <p>
 * （注：共享元素动画：在TestNewAnima和TestNewAnima3里边都有一个Button，只不过一个大一个小，从TestNewAnima跳转到TestNewAnima3时，我并没有感觉到Activity的跳转，
 * 只是觉得好像第一个页面的Button放大了，同理，当我从第二个页面回到第一个页面时，也好像Button变小了。OK，这就是我们的Activity共享元素。
 * 当两个Activity中有同一个控件的时候，我们便可以采用共享元素动画。
 * 使用共享元素动画的时候，我们需要首先给TestNewAnima和TestNewAnima3中的两个button分别添加Android:transitionName="mybtn"属性
 * 并且该属性的值要相同，这样系统才知道这两个控件是共享元素。）
 */

public class TestNewAnima extends Activity {

    private TextView txt;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_new_anima);

        txt = (TextView) findViewById(R.id.anima_txt);
        txt.setText("TestNewAnima");
        txt.setBackgroundColor(getResources().getColor(R.color.red));
        btn = (Button) findViewById(R.id.anima_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                //------A、非共享元素型：1、分解动画，2、滑动进入，3、淡入淡出
//                startActivity(new Intent(TestNewAnima.this, TestNewAnima2.class), ActivityOptions.makeSceneTransitionAnimation(TestNewAnima.this).toBundle());
                //------旧转场动画回顾
//                overridePendingTransition(R.animator.anim_in_from_top,R.animator.anim_out_from_bottom);

                //------B、共享元素型：4.1、共享元素动画（的单个共享元素）
                startActivity(new Intent(TestNewAnima.this, TestNewAnima3.class),
                        ActivityOptions.makeSceneTransitionAnimation(TestNewAnima.this, btn, "myBtn").toBundle());
                //------4.2 如果两个页面中有多个共享元素该怎么办呢？简单，android:transitionName属性还像上面一样设置，
                // 然后在启动Activity时我们可以通过Pair.create方法来设置多个共享元素
                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(TestNewAnima.this, Pair.create((View) txt, "myTxt"), Pair.create((View) btn, "myBtn"));
                startActivity(new Intent(TestNewAnima.this, TestNewAnima3.class), options.toBundle());
                addFragmentOfApp();
            }
        });
    }

    /**
     * 不使用v4包的话，最低API Level需要是11。
     */
    private void addFragmentOfApp() {
        TestAnimaFragment fragment = new TestAnimaFragment();
        android.app.FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //标准动画
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        //自定义动画
        //---a、ft.setCustomAnimations两参的：是API Level 11引入的两个动画的形式，即无法指定Back Stack栈操作时的转场动画。
//        ft.setCustomAnimations(R.animator.fragment_slide_left_enter,R.animator.fragment_slide_right_exit);
        //---b、ft.setCustomAnimations四参的：是API Level 13才有的，指定Back Stack栈操作时的转场动画。
        ft.setCustomAnimations(R.animator.fragment_slide_left_enter, R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter, R.animator.fragment_slide_right_exit);

        ft.add(Window.ID_ANDROID_CONTENT, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * 使用v4包，Fragment的使用不再局限于API Level 11之上
     * (注：在使用V4包中Fragment时，使用的切换动画效果，其动画文件中不能包含objectAnimator，Animator这类标签。
     * 如果必须要使用，请将工程中使用的V4包中Fragment相关类，换成源码中的Fragment相关类。)
     */
    private void addFragmentOfV4() {
//        TestAnimaFragment fragment = new TestAnimaFragment();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(R.animator.fragment_slide_left_enter,R.animator.fragment_slide_left_exit,
//                R.animator.fragment_slide_right_enter,R.animator.fragment_slide_right_exit);
//
//        ft.add(Window.ID_ANDROID_CONTENT,fragment);
//        ft.addToBackStack(null);
//        ft.commit();
    }
}
