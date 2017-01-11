package com.example.administrator.text1.ui.testVersionUpDate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2017/1/6.
 *
 * 检查版本更新
 */

public class TestVersionUpDate extends Activity {

    private TextView txtTitle;
    private TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text5);

        txtTitle = (TextView) findViewById(R.id.title);
        txtTitle.setText("测试版本更新");
        txtContent = (TextView) findViewById(R.id.btn);
        txtContent.setText("点击获取最新版本");
        txtContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VersionUpdater versionUpdater = new VersionUpdater();
                versionUpdater.checkNewVersion(TestVersionUpDate.this, null, true);
            }
        });
    }
}
