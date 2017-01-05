package com.example.administrator.text1.ui.testCompressImg;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.api.AddressApi;
import com.example.administrator.text1.model.address.AddressDetailsModel;
import com.example.administrator.text1.utils.ImageLoadUtil;
import com.example.administrator.text1.utils.ImgUtil;
import com.example.administrator.text1.utils.ObjCacheUtil;
import com.example.administrator.text1.utils.http.RestCallback;
import com.example.administrator.text1.utils.http.ServerResultCode;
import com.seaway.android.common.toast.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by hzhm on 2016/8/4.
 */
public class TestCompressImg extends Activity {

    private static final int REQUEST_CAMERA = 0x001;
    private static Uri frontCameraOutPutUri;
    private ImageView compressImg;
    private Button btn;
    private TextView testTxt;
    private File cameraOutFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_compressimg);

        //Android6.0以后所需要的指定的照片存储目录
        frontCameraOutPutUri = Uri.fromFile(new File(this.getExternalCacheDir(), "front"));

        compressImg = (ImageView) findViewById(R.id.compress_img);
        testTxt = (TextView) findViewById(R.id.compress_txt);
        btn = (Button) findViewById(R.id.compress_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestPerson person1 = ObjCacheUtil.get(TestPerson.class);
                if(person1 == null){
                    Toast.showToast(TestCompressImg.this,"error");
                    return;
                }
                testTxt.setText(person1.name + person1.age);
                Log.e("---person1---",person1.toString().length()+"");
//                Toast.showToast(TestCompressImg.this,"Success");

                //测试FileInputStream 与 BufferedInputStream 效率对比
//                compareFileStreamWithBufferFileStream();

                //调用Api发起网络请求（注：这里使用的是retrofit网络便捷请求框架）
                AddressApi.init();
                AddressApi.getAddressDetails(new RestCallback<AddressDetailsModel>() {
                    @Override
                    public void onSuccess(AddressDetailsModel model) {
                        Toast.showToast(TestCompressImg.this,model.name);
                        testTxt.setText(model.name);
                    }

                    @Override
                    public void onFail(ServerResultCode serverResultCode, String errorMessage) {
                        Toast.showToast(TestCompressImg.this,errorMessage);
                    }
                });
            }
        });

        TestPerson person = new TestPerson();
        ObjCacheUtil.save(person);


        if (TextUtils.isEmpty((CharSequence) ObjCacheUtil.get(String.class))) {
            File file = ObjCacheUtil.get(File.class);
            ImageLoadUtil.loadFileImage(this, compressImg, file, R.drawable.trccard, R.drawable.trccard);
        }
        compressImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到MediaStore(多媒体数据库)相关指定界面
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //MediaStore.EXTRA_OUTPUT：指的是输出，即表示拍照完成后存储的文件地址
                intent.putExtra(MediaStore.EXTRA_OUTPUT, frontCameraOutPutUri);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
    }

    /*
    * 测试FileInputStream 与 BufferedInputStream 效率对比
     */
    private void compareFileStreamWithBufferFileStream(){
        File src = cameraOutFile;
        File dest = new File("/storage/emulated/0/Android/data/com.example.administrator.text1/cache/copyTest.txt");
        try {
            if (!dest.exists()){
                dest.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //test copy using FileStream
        int startTime = (int) System.currentTimeMillis();
        TextFileOperator.copyFileStream(src, dest);
        int endTime = (int) System.currentTimeMillis();

        //test copy using BufferedStream
        startTime = (int) System.currentTimeMillis();
        TextFileOperator.copyBufferedFileStream(src, dest);
        endTime = (int) System.currentTimeMillis();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                cameraOutFile = new File(frontCameraOutPutUri.getPath());
                //打印压缩前文件长度大小及其文件路径
                Log.e("cameraOutFile---------", cameraOutFile.length() + "");
                //压缩图片
                File compressFile = ImgUtil.compress(cameraOutFile, 50, 1920 * 1080);
                boolean aa = ObjCacheUtil.save(compressFile);
                ObjCacheUtil.save(compressFile);

                //打印压缩后文件长度大小及其文件路径
                Log.e("compressFile---------", compressFile.length() + "");
                String frontImgTxt = compressFile.toURI().getPath();
                ImageLoadUtil.loadFileImage(this, compressImg, compressFile, R.drawable.trccard, R.drawable.trccard);
            }
        }
    }
}
