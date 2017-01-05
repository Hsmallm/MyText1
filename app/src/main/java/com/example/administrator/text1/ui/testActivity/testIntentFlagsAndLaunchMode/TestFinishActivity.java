package com.example.administrator.text1.ui.testActivity.testIntentFlagsAndLaunchMode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/12/5.
 * 功能描述：intent.setFlags()给intent设置标记，从而控制同一栈堆task里面Activity
 * (注：在Android系统中，一个application的所有Activity默认有一个相同的affinity（亲密关系,相似之处）。
 * 也就是说同一个应用程序的的所有Activity倾向于属于同一个task。但是我们并不能说Android里一个应用程序只有一个任务栈)
 * 1.FLAG_ACTIVITY_CLEAR_TOP：简而言之，跳转到的activity若已在栈中存在，则将其上的activity都销掉
 * 2.FLAG_ACTIVITY_NEW_TASK：a、如果D这个Activity在Manifest.xml中的声明中添加了Task affinity，系统首先会查找有没有和D的Task affinity相同的task栈存在，如果有存在，将D压入那个栈，
                             b、如果不存在则会新建一个D的affinity的栈将其压入。
                             c、如果D的Task affinity默认没有设置，则会把其压入栈1，变成：A B C D，这样就和不加FLAG_ACTIVITY_NEW_TASK标记效果是一样的了。
 * 3.FLAG_ACTIVITY_NO_HISTORY：简而言之，跳转到的activity不压在栈中
 * 4.FLAG_ACTIVITY_SINGLE_TOP：简而言之，目标activity已在栈顶则跳转过去，不在栈顶则在栈顶新建activity。
                               情况一：如果某个Activity自己激活自己，即任务栈栈顶就是该Activity，则不需要创建
                               情况二：其余情况都要创建Activity实例，就和上面的standard一样
 * 5.FLAG_ACTIVITY_CLEAR_TASK：这个标识将导致,在此activity启动之前，任何与此activity相关联的task都会被清除。
 * 也就是说，此activity将变成一个空栈中新的最底端的activity，所有的旧activity都会被finish掉
 * 这个标识仅仅和FLAG_ACTIVITY_NEW_TASK联合起来才能使用。即为：Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
 */

public class TestFinishActivity extends Activity {

    private TextView describeTxt;
    private Button btn;

    public static Intent newIntent(Activity activity){
        Intent intent = new Intent(activity,TestFinishActivity.class);
        return intent;
    }

    public static Intent newIntentAddFlags(Activity activity) {
        Intent intent = new Intent(activity,TestFinishActivity.class);
        //这个标识将导致：在此activity启动之前，任何与此activity相关联的task都会被清除。
        // 也就是说，此activity将变成一个空栈中新的最底端的activity，所有的旧activity都会被finish掉;这个标识仅仅和FLAG_ACTIVITY_NEW_TASK联合起来才能使用。
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_base);

        describeTxt = (TextView) findViewById(R.id.describe);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        finish();
    }
}
