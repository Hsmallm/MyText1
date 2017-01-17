package com.example.administrator.text1.ui.testScanCode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2017/1/17.
 *
 * 功能描述：实现二维码扫描器功能...
 */

public class TestScanCodeActivity extends Activity {

    private static final int SCANCODE_REQUEST_CODE = 1;

    private Button btn;
    private TextView txt;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scancode);

        btn = (Button) findViewById(R.id.button1);
        txt = (TextView) findViewById(R.id.result);
        img = (ImageView) findViewById(R.id.qrcode_bitmap);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestScanCodeActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANCODE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANCODE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    txt.setText(bundle.getString("result"));
                    img.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
        }
    }
}
