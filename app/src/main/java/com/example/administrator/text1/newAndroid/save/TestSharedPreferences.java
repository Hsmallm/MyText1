package com.example.administrator.text1.newAndroid.save;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.ToastUtil;

/**
 * @author HuangMing on 2017/11/24.
 *         功能描述：使用SharedPreferences实现数据持久化
 *         文件存放位置：/data/data/<package name>/shared_prefs/
 *         1、Context类中通过getSharedepreferences()方法来获取SharedPreferences实例，传入两个参数，参数一：用于指定当前文件的文件名称
 *            参数二：用于指定操作模式（注：当前有且只有一种操作模式MODE_PRIVATE,为默认操作模式，表示只有当前应用程序才可以对该文件进行存储）
 *         2、Activity中通过getPreferences()方法来获取SharedPreferences实例，传入参数只有一个，只接受传入的操作模式，该方法会自动将当前的活动类名作为文件名称
 *         3、PreferenceManager类中使用getDefaultSharedPreferences()方法来获取SharedPreferences实例，参入参数一个，它接收一个Context参数，会直接将当前应用程序的包名前缀作为文件名称
 */

public class TestSharedPreferences extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sharedpreferences);
        Button btn = (Button) findViewById(R.id.btn);
        final int[] age = {24};
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age[0] = age[0] + 1;
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("name", "HXM");
                editor.putString("age", age[0] + "");
                editor.apply();
            }
        });
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                String name = sharedPreferences.getString("name", "");
                String age = sharedPreferences.getString("age", "");
                ToastUtil.showNormalToast("Show Now" + "name：" + name + ",age：" + age);
            }
        });
    }
}
