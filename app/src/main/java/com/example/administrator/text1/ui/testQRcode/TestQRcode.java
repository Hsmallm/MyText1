//package com.example.administrator.text1.ui.testQRcode;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.administrator.text1.R;
//import com.seaway.android.common.toast.Toast;
//import com.xys.libzxing.zxing.activity.CaptureActivity;
//import com.xys.libzxing.zxing.encoding.EncodingUtils;
//
///**
// * 功能描述：扫描二维码和生成二维码
// * 1、扫描二维码
// * 2、生成二维码
// * (注：这里这里我们引用了第三方资源类库libzxing：它集成了扫描二维码和生成二维码的所有功能)
// * Created by hzhm on 2016/6/27.
// */
//public class TestQRcode extends Activity {
//
//    private Button btn;
//    private TextView txt;
//
//    private EditText editText;
//    private Button btnCreate;
//    private CheckBox checkBox;
//    private ImageView img;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_qr_code);
//
//        btn = (Button) findViewById(R.id.btn);
//        txt = (TextView) findViewById(R.id.txt);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //1.1、跳转到CaptureActivity：扫描二维码界面
//                startActivityForResult(new Intent(TestQRcode.this, CaptureActivity.class), 0);
//            }
//        });
//        editText = (EditText) findViewById(R.id.edt);
//        btnCreate = (Button) findViewById(R.id.btn_create);
//        checkBox = (CheckBox) findViewById(R.id.checkbox);
//        img = (ImageView) findViewById(R.id.img);
//        btnCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String input = editText.getText().toString();
//                if(input.equals("")){
//                    Toast.showToast(TestQRcode.this,"请输入生成的文字");
//                }else {
//                    //2、EncodingUtils.createQRCode():通过相关的输入信息获取生成的二维码
//                    Bitmap bitmap = EncodingUtils.createQRCode(input,500,500,checkBox.isChecked()?
//                            BitmapFactory.decodeResource(getResources(),R.drawable.ic_logo):null);
//                    img.setImageBitmap(bitmap);
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //1.2、如果扫描成功，则获取bundle传来的信息
//        if (resultCode == RESULT_OK) {
//            Bundle bundel = data.getExtras();
//            String result = bundel.getString("result");
//            txt.setText(result);
//        }
//    }
//}
