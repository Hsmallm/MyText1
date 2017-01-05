package com.example.administrator.text1.ui.testActivity.testIntentFlagsAndLaunchMode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/12/2.
 * 功能描述：Activity的四种启动模式
 * 一、启动模式介绍
 　　启动模式简单地说就是Activity启动时的策略，在AndroidManifest.xml中的标签的android:launchMode属性设置；
   二、任务栈
 　　每个应用都有一个任务栈，是用来存放Activity的，功能类似于函数调用的栈，
     先后顺序代表了Activity的出现顺序；比如Activity1-->Activity2-->Activity3,则任务栈为：
 (1)standard模式（默认）：每次激活Activity时(startActivity)，都创建Activity实例，并放入任务栈；
 (2)singleTop模式：
    情况一：如果某个Activity自己激活自己，即任务栈栈顶就是该Activity，则不需要创建
    情况二：其余情况都要创建Activity实例，就和上面的standard一样
 (3)singleTask模式：如果要激活的那个Activity在任务栈中存在该实例，则不需要创建，只需要把此Activity放入栈顶，并把该Activity以上的Activity实例都pop；
      （注：可以用来退出整个应用。将主Activity设为SingTask模式（即：表示当前的Activity就在整个栈的栈底了）；
       然后在要退出的Activity中转到主Activity，然后重写主Activity的onNewIntent函数，并在函数中加上一句finish。）
 (4)singleInstance模式：如果应用1的任务栈中创建了MainActivity实例，如果应用2也要激活MainActivity，则不需要创建，两应用共享该Activity实例；
      （注：此模式一般用于加载较慢的，比较耗性能且不需要每次都重新创建的Activity。）
 */
public class TestActivity2 extends Activity {

    private TextView txtTitle;
    private TextView txtDescribe;
    private Button btn;

    public static Intent newIntent(Activity activity){
        Intent intent = new Intent(activity,TestActivity2.class);
        return intent;
    }

    public static Intent newIntentAddFlags(Activity activity){
        Intent intent = new Intent(activity,TestActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_base);

        txtTitle = (TextView) findViewById(R.id.title);
        txtDescribe = (TextView) findViewById(R.id.describe);
        txtDescribe.setText("Activity2");
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TestActivity3.newIntent(TestActivity2.this));
            }
        });
    }
}
