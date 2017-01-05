package com.example.administrator.text1.ui.testSlide.textScroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * 功能描述：测试Scroller平滑滚动的一个Helper类的应用
 * Created by HM on 2016/5/30.
 */
public class TextScrollerActivity extends Activity {

    private Button btn;
    private TextScroller textScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_scroller);

        textScroller = (TextScroller) findViewById(R.id.textScroller);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textScroller.beginScroll();
            }
        });
    }
}

