package com.example.administrator.text1.ui.testScreenShot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.ScreenShot;

import java.io.File;

/**
 * @author HuangMing on 2018/3/15.
 */

public class TestGetSdCardPic extends AppCompatActivity {

    private ScrollView scrollView;
    private ImageView imageView;
    private View titleView;

    private String filePath = "/sdcard/Pictures/TestGetSdCardPic.png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_money);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        imageView = (ImageView) findViewById(R.id.share_money_img);
        titleView = findViewById(R.id.title_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        check();
    }

    private void check() {
        View view = this.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        if (b1 == null) {
            imageView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    check();
                }
            }, 50);
        } else {
            screenShot();
        }
    }

    private void screenShot() {
        ScreenShot.savePic(ScreenShot.getScrollViewBitmap(scrollView), "sdcard/Pictures/TestGetSdCardPic.png");

        File file = new File(filePath);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            imageView.setImageBitmap(bitmap);
        }
    }
}
